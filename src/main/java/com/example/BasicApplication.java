package com.example;

import com.example.service.ServicioImpresor;
import java.util.concurrent.ScheduledFuture;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.concurrent.DefaultManagedTaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

@SpringBootApplication
@EnableScheduling
//@EnableAutoConfiguration
public class BasicApplication implements ICancelador{

   private final static Logger LOG = LoggerFactory.getLogger(BasicApplication.class);
   
   //@Autowired
   //@Qualifier("pool")
   //private TaskScheduler ejecutor;
   
   @Autowired
   private ServicioImpresor servicioImpresor;

   private ScheduledFuture<?> futuroInicial;
   
   @Autowired
   private TaskScheduler ejecutor;
   
   @Bean
    public TaskScheduler creaTreahPool(){
       
       LOG.info("Esqueduleando");
      TaskScheduler scheduler
          = new ConcurrentTaskScheduler();
	
      futuroInicial = scheduler.schedule(() -> {
	 LOG.info("En el Run");
	 servicioImpresor.ejecutaServicio();
      }, new CronTrigger("1 * * * * ?"));	
	
        return scheduler;
    }
    
    
    
    public void cancelar()
    {
       futuroInicial.cancel(true);
    }
    
    
    
    
    
    
    
    
    

   public static void main(String[] args) {
      SpringApplication.run(BasicApplication.class, args);
   }

   @Override
   public void cancelaAnterior() {
      futuroInicial.cancel(true);
   }

   @Override
   public void actualizaTarea(String cron) {
      
      LOG.info("Programando para {}", cron);
      futuroInicial = ejecutor.schedule( new Runnable(){
	 @Override
	 public void run() {
	    servicioImpresor.ejecutaServicio();

	 }
	 
      }, new CronTrigger(cron));
   }
}
