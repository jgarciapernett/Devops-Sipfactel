import { Component, Input, OnInit, Output, EventEmitter } from '@angular/core';
import { PolizasService } from 'src/app/services/polizas.service';

@Component({
  selector: 'app-detalle-transaccion',
  templateUrl: './detalle-transaccion.component.html',
  styleUrls: ['./detalle-transaccion.component.css']
})
export class DetalleTransaccionComponent implements OnInit {

  @Input() dniDocumento: number;
  @Input() tipo: string
  @Output() next = new EventEmitter<any>();

  public info: any = {};

  constructor(private service: PolizasService) {}

  ngOnInit() {
    this.consultarDetalle();
  }

  private consultarDetalle(): void {
    this.service.transaccion(this.dniDocumento,this.tipo)
        .subscribe((resp: any) => {
          this.info = resp.resultado;
        });
  }

  siguiente(): void {
    this.next.emit();
  }

}
