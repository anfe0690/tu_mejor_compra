package com.anfe0690.tu_mejor_compra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.Set;

@ServerEndpoint("/michat")
public class MiChatEndpoint {

	// Logger
	private static final Logger logger = LoggerFactory.getLogger(MiChatEndpoint.class);
	// Keys
	public static final String K_TIPO = "K_TIPO";
	// Values
	public static final String V_TIPO_UNION = "V_TIPO_UNION";
	public static final String V_TIPO_MENSAJE = "V_TIPO_MENSAJE";
	public static final String V_TIPO_LISTA_NOMBRES = "V_TIPO_LISTA_NOMBRES";

	@OnOpen
	public void onOpen(Session session, EndpointConfig conf) {
		logger.trace("onOpen() id={}", session.getId());
	}

	@OnMessage
	public void onMessage(Session session, String msg) {
		logger.trace("onMessage() Id={} json='{}'", session.getId(), msg);

		JsonReader jsonReader = Json.createReader(new StringReader(msg));
		JsonObject jsonEntrada = (JsonObject) jsonReader.read();

		StringWriter stWriter = new StringWriter();
		switch (jsonEntrada.getString(K_TIPO)) {
			case V_TIPO_UNION: {
				session.getUserProperties().put("nombre", jsonEntrada.getString("nombre"));

				// Enviar mensaje de bienvenida
				JsonObject jsonSalida = Json.createObjectBuilder()
						.add(K_TIPO, V_TIPO_MENSAJE)
						.add("nombre", "Sistema")
						.add("mensaje", "Bienvenido " + jsonEntrada.getString("nombre") + " a Mi Chat!")
						.build();
				try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
					jsonWriter.writeObject(jsonSalida);
				}
				enviarATodos(session.getOpenSessions(), stWriter);

				// Enviar lista de nombres
				stWriter = new StringWriter();
				JsonArrayBuilder jab = Json.createArrayBuilder();
				for (Session s : session.getOpenSessions()) {
					jab.add((String) s.getUserProperties().get("nombre"));
				}
				jsonSalida = Json.createObjectBuilder()
						.add(K_TIPO, V_TIPO_LISTA_NOMBRES)
						.add("listaNombres", jab)
						.build();
				try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
					jsonWriter.writeObject(jsonSalida);
				}
				enviarATodos(session.getOpenSessions(), stWriter);

				break;
			}
			case V_TIPO_MENSAJE: {
				JsonObject jsonSalida = Json.createObjectBuilder()
						.add(K_TIPO, V_TIPO_MENSAJE)
						.add("nombre", jsonEntrada.getString("nombre"))
						.add("mensaje", jsonEntrada.getString("mensaje"))
						.build();
				try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
					jsonWriter.writeObject(jsonSalida);
				}
				enviarATodos(session.getOpenSessions(), stWriter);
				break;
			}
			default: {
				logger.warn("json='{}'", msg);
			}
		}
	}

	@OnError
	public void onError(Session session, Throwable error) {
		logger.error("onError()", error);
	}

	@OnClose
	public void onClose(Session session, CloseReason reason) {
		logger.trace("close() id={} nombre=\"{}\"", session.getId(), session.getUserProperties().get("nombre"));
		Set<Session> ss = session.getOpenSessions();
		Iterator<Session> it = ss.iterator();
		while(it.hasNext()){
			Session s = it.next();
			if(s.getId().equals(session.getId())){
				it.remove();
				break;
			}
		}

		// Notificar que el usuario se ha desconectado
		StringWriter stWriter = new StringWriter();
		JsonObject jsonSalida = (JsonObject) Json.createObjectBuilder()
				.add(K_TIPO, V_TIPO_MENSAJE)
				.add("nombre", "Sistema")
				.add("mensaje", "El usuario " + session.getUserProperties().get("nombre") + " se ha desconectado.")
				.build();
		try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
			jsonWriter.writeObject(jsonSalida);
		}
		enviarATodos(ss, stWriter);

		// Actualizar nombre conectados
		stWriter = new StringWriter();
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for (Session s : ss) {
			jab.add((String) s.getUserProperties().get("nombre"));
		}
		jsonSalida = (JsonObject) Json.createObjectBuilder()
				.add(K_TIPO, V_TIPO_LISTA_NOMBRES)
				.add("listaNombres", jab)
				.build();
		try (JsonWriter jsonWriter = Json.createWriter(stWriter)) {
			jsonWriter.writeObject(jsonSalida);
		}
		enviarATodos(ss, stWriter);
	}

	private void enviarATodos(Set<Session> ss, StringWriter stWriter) {
		for (Session s : ss) {
			if (s.isOpen()) {
				try {
					s.getBasicRemote().sendText(stWriter.toString());
				} catch (IOException ex) {
					logger.error(null, ex);
				}
			}
		}
	}
}
