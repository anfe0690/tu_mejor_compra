/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anfe0690.tu_mejor_compra;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andres
 * @param <T>
 */
public class MiLogger {

    private Logger logger;
    private String classSimpleName;
    
    public MiLogger(Class clase) {
        classSimpleName = clase.getSimpleName();
        logger = Logger.getLogger(clase.getName());
    }
    
    public void log(String s){
        logger.log(Level.INFO, "{0}: {1}", new Object[]{classSimpleName, s});
    }
    
    public void error(Throwable t){
        logger.log(Level.SEVERE, t.getLocalizedMessage(), t);
    }

}
