import { Injectable } from '@angular/core';
import { Idle, DEFAULT_INTERRUPTSOURCES } from '@ng-idle/core';

@Injectable({
  providedIn: 'root'
})
export class IdleConfigurationService {

  constructor(
    private idle: Idle,
  ) {

    idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);
  }


  timerIdle(time?) {
    
    // establece un tiempo de inactividad de 180 segundos (3 min)
    this.idle.setIdle(time);
     console.log(time)
    //  establece un tiempo de espera de 180 segundos. Después de 10 segundos de inactividad, el usuario sera deslogueado.
    this.idle.setTimeout(60);
    // this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);
    //DEFAULT_INTERRUPTSOURCES establece las interrupciones predetermÑinadas, en este caso, cosas como clics, desplazamientos, toques del documento
    //  console.log('DEFAULT_INTERRUPTSOURCES', DEFAULT_INTERRUPTSOURCES[0].events);


  }

}
