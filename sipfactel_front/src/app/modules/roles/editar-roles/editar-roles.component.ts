import { Component, OnInit, Input, SimpleChanges, DebugElement } from '@angular/core';

//#region servicios
import { RolesService } from '../../../services/roles.service';
import { UtilidadesService } from '../../../services/utilidades.service';
//#endregion servicios

//#region modelos
import { Lista } from 'src/app/models/lista';
import { agregar } from 'src/app/models/rolesAgregar';
import { roles } from '../../../models/roles';
import { menus } from 'src/app/models/menusRol';
import { permisos } from 'src/app/models/permisos';
//#endregion modelos

//#region rutas
import { Router, ActivatedRoute } from '@angular/router';
//#endregion rutas

//#region  bibliotecas
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { menusHijos } from 'src/app/models/menusHijosRoles';
import { Categoria } from 'src/app/models/Polizas/categoria';
import { selectInicial } from '../../../directives/validators/select.validator';
//#endregion bibliotecas
@Component({
  selector: 'app-editar-roles',
  templateUrl: './editar-roles.component.html',

})
export class EditarRolesComponent implements OnInit {

  @Input() rol: roles;
  @Input() roleRole: Number;
  public esModulo: Lista[];
  public menusHijos : menusHijos []=[]

  public modulos: menus[] = [];
  public modulosEntrantes: menus[] = [];
  public hijosNull : menus [] = [];
  public role: menus;
  public cat: Categoria [] = []

  public hijosApintar: menus;
  subpermisos = [{ id: 1, nombre: 'crear' }, { id: 2, nombre: 'modificar' }, { id: 3, nombre: 'consultar' }
]

  public esRolesValido: boolean;
  public mensaje: boolean;

  public frmEditarRoles: FormGroup;
  public EditarRoles: agregar;
  public idResivido: number;
  public resultado: string;

  constructor(
    private rolesService: RolesService,

    public formBuilder: FormBuilder,
    private router: Router,
    private activedRoute: ActivatedRoute,
  ) {
    this.obtenerCategorias()

  }

  ngOnInit() {
    this.inicializacion();
    this.validaciones();
  }

  ngOnChanges(changes: SimpleChanges): void {// captura el valor entrante del id
    if (changes.EditarRoles.currentValue != undefined)
      this.EditarRoles = changes.roleRole.currentValue;
  }

  obtenerCategorias(){

    this.rolesService.categorias().subscribe((res : any ) => {

      this.cat = res.resultado

    })
  }

  cancelar() {

    this.router.navigate(['/Roles/home'])

  }

  CampoCheckeadoPermisos( nombre: string , menuMenu: number){
    const EsSeleccionado = this.EditarRoles.modulos.some(item => {
      if(item.idMenu === menuMenu && item[nombre] === 1 ){

        return  true
      }else{
        return false
      }


    })
  return EsSeleccionado

  }
  obtenerRolPorId() { // Servicio que lista la informacion del formulario por el id obtenido


    this.roleRole = Number(this.activedRoute.snapshot.params.roleRole.toString());
    this.rolesService
      .obtenerOpcionPorId(this.roleRole)
      .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
        this.resultado = JSON.stringify(resp);
        this.EditarRoles = resp.resultado
        this.modulosEntrantes = []
        this.EditarRoles.modulos.map(item => {
          if(item.modificar === 1 || item.crear === 1 || item.consultar ===1 ){
            this.modulosEntrantes.push(item)
            this.EditarRoles.modulos = this.modulosEntrantes

          }

          item.hijos.map(data => {
            if(data.modificar === 1 || data.crear === 1 || data.consultar ===1){

              this.menusHijos.push(data)
            }

          })


        })

        //   this.esModulo = resp.resultado.modulos;
      });

    }


  ObtenerModulos() { // Servicio que lista los permisos
    this.rolesService.listarMenus().subscribe((res: any) => { // Solicitud con exitó o fallo
      this.menusHijos = []
      this.resultado = JSON.stringify(res);
      this.modulos = res.resultado;
        this.modulos.forEach(item => {
          item.hijos.map(hijo => {
            this.menusHijos.push(hijo)
          } )
            // if(item.menuMenu == 'Remediación' || item.menuMenu == 'Administración' || item.menuMenu == 'Usuarios y Roles' || item.menuMenu == 'Reportes'  ){
            //   item.menuMenu = null
            //             }
          },
          )
          // console.log(this.menusHijos);




  }
    )
  }


  editarRol() { // Servicio que edita los campos del formulario

    this.asignarHijoAPadre()
    debugger;
    this.comprobarValideRoles();
    debugger;

    this.rolesService
      .EditarUsuario(this.EditarRoles)
      .subscribe((res: any) => { // Solicitud completada con éxito o fallo

        Swal.fire({
          type: "success",
          title: "Operación Exitosa",
          text: res.mensajeEstado
        });
        this.router.navigate(['/Roles/home'])
      }),
      (error: HttpErrorResponse) => {
      };
  }

  comprobarValideRoles() { // Validacion de que minimo un permiso este seleccionado
    this.esRolesValido = this.EditarRoles.modulos.length > 0;
  }


  esRolSeleccionado(rolId: string) { // Verifica los permisos que esten seleccionados
    const esSeleccionado = this.EditarRoles.modulos.some((role: permisos) => {

      return role.idMenu.toString() === rolId.toString();
    });
    return esSeleccionado;

  }




  Permisos(event: Event, idMenu: number, menuMenu: string, consultar: number, modificar: number, crear: number) { // función de los checkbox
    const eventTarget = event.target as HTMLInputElement;

    if (eventTarget.checked) {   // Valida que la informacion del checkbox obtenida tenga los mismos paramtros del modelo
      this.role = new permisos(idMenu, menuMenu, consultar, modificar, crear);

      this.EditarRoles.modulos.push(this.role);
    }
    else {

      this.EditarRoles.modulos = this.EditarRoles.modulos.filter((role: permisos) => { // Elimina el array cuando se quita un check
        return role.idMenu.toString() !== idMenu.toString();

      });
    }
    this.comprobarValideRoles();

  }



  subPermisos(idMenu: number, nombre: string, menuMenu: string, consultar: number, modificar: number, crear: number) {// Fución de los checkbox internos de los permisos
    const eventTarget = event.target as HTMLInputElement;
    if(eventTarget.checked === undefined){
      eventTarget.checked = true
    }
    if (eventTarget.checked) { // Valida el check seleccionado y revisa la que sea propio del id del permiso

      if (nombre === "crear") {

        crear = 1
        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.crear = crear
          } else (item)
        })

      } else if (nombre === "modificar") {

        modificar = 1

        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.modificar = modificar
          } (item)

        })

      } else if (nombre === "consultar") {

        consultar = 1
        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.consultar = consultar
          } else (item)

        })

      }
      this.role = new permisos(idMenu, menuMenu, consultar, crear, modificar)

    }
    else { // Valida que el check que se quito cambie su valor
      if (nombre == 'crear') {
        crear = 0
        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.crear = crear
          } else (item)

        })

      } else if (nombre == 'modificar') {
        modificar = 0
        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {

            item.modificar = modificar
          } else (item)

        })

      } else if (nombre == 'consultar') {
        consultar = 0
        this.EditarRoles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.consultar = consultar
          } else (item)


        })

      }

    }

  }
  asignarHijoAPadre(){ // se igualan los pagres que vienen con hijos a un valor uno en sus permisos
    this.modulos.map(datos => {

      datos.hijos.map(hijoEstandar => { // mapeo de los hijos que vienen de modulos
        this.EditarRoles.modulos.map(hijoEntrante =>{ // mapeo de los hijos checkeados
          if(hijoEntrante.idMenu === hijoEstandar.idMenu){ // validacion para darle valores a los hijos  de modulos
            datos.consultar = hijoEntrante.consultar
            datos.modificar = hijoEntrante.modificar
            datos.crear = hijoEntrante.crear
            hijoEstandar.modificar = hijoEntrante.modificar
            hijoEstandar.crear =hijoEntrante.crear
            hijoEstandar.consultar = hijoEntrante.consultar
          }
        })
      })
    })
    this.EditarRoles.modulos.map(data => { // mapeo de los modulos seleccionados para entre hijos y padres

      if( data.idMenu === 2 || data.idMenu === 1 || data.idMenu === 7 || data.idMenu === 17 || data.idMenu === 18 || data.idMenu === 0 || data.idMenu === 23){
        this.hijosNull.push(data)  // funcion para enviar los padres sin hijos a una variable
      }}
    )

  this.EditarRoles.modulos = [] // limpieza de los modelos seleccionados para asignar nuevos valores
  this.NuevosHijos()
}

NuevosHijos(){

  this.modulos.forEach(datas => { // mapeo de los padres con hijos que tienen valores en uno
    datas.hijos.map(cambio => {
      if(cambio.crear == 1 || cambio.modificar == 1 || cambio.consultar == 1){
        datas.consultar = 1
        datas.crear = 1
        datas.modificar = 1
      }
    })
      if(datas.consultar == 1 || datas.crear == 1 || datas.modificar == 1)
      {
        datas.consultar = 0
        datas.crear = 0
        datas.modificar = 0
        this.EditarRoles.modulos.push(datas) // asignacion del padre con valores cerro a nuestro arreglo a enviar
      }
    })
    this.padres()
  }

  padres(){

    if(this.hijosNull.length > 0){ // validacion de que si hay mas de 0 padres sin hijo seleccionado
      this.hijosNull.map(padre => {

        this.EditarRoles.modulos.push(padre) // asignamos el padre sin hijos a nuestro arreglo
      })

    }
  }
  inicializacion() { // inicializacion de parametros pantalla
    this.obtenerRolPorId();
    this.esRolesValido = true;
    this.EditarRoles = new agregar();
    this.ObtenerModulos();
  }

    validaciones() { // Validacion de campos requeriodos y su maximo de logitud
    this.frmEditarRoles = this.formBuilder.group({
      roleNombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      roleDescripcion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      roleEsta: this.formBuilder.control(0, [Validators.required,selectInicial]),
      cat: this.formBuilder.control(0, [Validators.required,selectInicial]),
      permisos: this.formBuilder.control(false, [Validators.required]),

    });


  }


}
