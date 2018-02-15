/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.service;

import com.example.ICancelador;
import java.util.concurrent.ScheduledFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

/**
 *
 * @author alvaro
 */
@Service
@Scope( "prototype")
public class ServicioImpresor {
   
   private final static Logger LOG = LoggerFactory.getLogger(ServicioImpresor.class);
   
   @Autowired
   private ICancelador cancelador;
   private ScheduledFuture<?> futuro;
   private static int minutoNuevo = 1;
   
   public void ejecutaServicio()
   {
      LOG.info("Me ejecuto");
      
      cancelador.cancelaAnterior();
      minutoNuevo++;
      cancelador.actualizaTarea(minutoNuevo+" */"+((minutoNuevo%5)+1)+" * * * ?");
      
   }
   
   
   
   public void reesquedulea()
   {
      
   }
   
   public void registraServicioInicial( ScheduledFuture<?> futuro)
   {
      this.futuro = futuro;
   }
   
   
}
