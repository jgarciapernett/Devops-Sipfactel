import { Component, OnInit,} from '@angular/core';
import { Subject } from 'rxjs/internal/Subject';

//#region  modelos

//#region modelos
import { Lista } from '../../../models/lista';
import { menus } from '../../../models/menusRol';
import { roles } from 'src/app/models/roles';
//#endregion modelos

//#region  servicios
import { RolesService } from 'src/app/services/roles.service';
import { UtilidadesService } from 'src/app/services/utilidades.service';
//#endregion servicios

//region rutas
import { Router } from '@angular/router';
//#endregion rutas

//#endregion bibliotecas

import { FormControl, FormBuilder, FormGroup, Validators, NumberValueAccessor } from '@angular/forms';
import { Categoria } from 'src/app/models/Polizas/categoria';
//#region bibliotecas
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
})
export class HomeComponent implements OnInit {
  public opcionesFiltro$: Subject<roles[]> = new Subject<roles[]>();
  public permisos: roles[] = [];
  public lista: Lista[][];
  public modulos: menus[];
  public filtroTabla = new FormControl('');
  public cat : Categoria[];
  public resultado: string;
  public nombre: string;
  public roleNombre: string;
  public categoria: string;

  public mostrar: boolean;
  public filtroBusqueda: boolean;
  public formularioAgregar: boolean;
  public formularioEditar: boolean;
  public agrego: boolean;

  public Roles: roles;
  public idRolAlEditar: number;
  public roleRole: number;
  public frmBusqueda: FormGroup;

  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;

  constructor(
    private rolesService: RolesService,
    private utilidadesService: UtilidadesService,
    private router: Router,
    public formBuilder: FormBuilder,


  ) {
    this.ObtenerPermisos('roles');
    this.obtenerCategorias()
  }


  ngOnInit() {
    this.inicializacion();
    this.validaciones();
    this.obtenerRoles()

  }
  obtenerCategorias(){
    this.rolesService.categorias().subscribe((res : any ) => {

      this.cat = res.resultado
     
  })
}
 
  FormularioAgregarRoles($event) {// Funcion del boton para ir al formulario agregar

    this.router.navigate(['/Roles/agregar'])
    this.Roles.agrego = $event;
    if (this.Roles.agrego !== undefined) { 
      this.obtenerTablaRoles();
    }
  }

  FormularioEditarRoles(roleRole: number) {// Funcion del boton para ir al formulario editar con el parametro

    this.router.navigate(['/Roles/editar', roleRole])

  }


  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que tenga
    
    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => { // Mapeo de los modulos y sus respectivos permisos
      item.menuHijos.map(alias => {
        if( alias.menuAlias === menuAlias){
          this.esCrear = alias.crear;
          this.esConsultar = alias.consultar;
          this.esEditar = alias.modificar;
        }
    
    
  
  


  }) })
}

  obtenerTablaRoles() { // Servicio que lista la informacion de la tabla de roles
    
 
    if(this.roleNombre === ""){
      this.obtenerRoles()
    }
    if (this.roleNombre !== undefined && this.roleNombre !== "") { // Validacion para de que el nombre del rol viene diligenciado
      this.rolesService
        .obtenerRoles(this.roleNombre)
        .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.permisos = resp.resultado;
          this.permisos.forEach(item => { // Mapeo de los permisos que traen los roles para que los liste  en la tabla
            this.cat.map(nombre => {
        
                if(item.categoria == nombre.cat){
                     item.categoriaID = nombre.descripcion
                }
        
              })
          
            var modulosStr = ' ';

            item.modulos.forEach(itemModulo => {
              if(itemModulo.crear == 0 && itemModulo.consultar == 0 && itemModulo.modificar == 0){



              }else {

                modulosStr += '*' + itemModulo.menuMenu + '. ';
              
}
            });


            item.modulosStr = modulosStr;


          }

          )
        });
    } else {
      this.obtenerRoles()
    }

    this.roleNombre = ''
  }


  obtenerRoles() { // Servicio que lista la informacion de la tabla de roles
    
    if (this.roleNombre == undefined || this.roleNombre == "") { // Validacion para de que el nombre del rol viene diligenciado
      this.rolesService
        .obtenerRolesTodos()
        .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
          this.resultado = JSON.stringify(resp);
          this.permisos = resp.resultado;
          this.permisos.forEach(item => { // Mapeo de los permisos que traen los roles para que los liste  en la tabla
            this.cat.map(nombre => {
        
                if(item.categoria == nombre.cat){
                     item.categoriaID = nombre.descripcion
                }
        
              }) // Mapeo de los permisos que traen los roles para que los liste  en la tabla
            var modulosStr = ' ';

            item.modulos.forEach(itemModulo => {
              if(itemModulo.crear == 0 && itemModulo.consultar == 0 && itemModulo.modificar == 0){



              }else {

                modulosStr += '*' + itemModulo.menuMenu + '. ';
              
}
});

            item.modulosStr = modulosStr;


          }

          )
        });
      this.mostrar = true;
    } else {
      this.mostrar = false;
    }
    this.obtenerCategorias()

  }

  ObtenerModulos() { // Servicio que lista los modulos que podra tener permiso de acceso cada rol

    this.rolesService
      .listarMenus()
      .subscribe((resp: any) => { // Solicitud completada con éxito o fallo
        this.resultado = JSON.stringify(resp);

        this.permisos = resp.resultado;





      });
  }

  inicializacion() { // inicializacion de pantallas
    this.nombre = "";
    this.mostrar = true;
    this.filtroBusqueda = true;
    this.formularioAgregar = false;
    this.formularioEditar = false;
    this.agrego = true;
    this.Roles = new roles();
  }

  validaciones() { // Valida la longitud del campo nombre del rol
    this.frmBusqueda = this.formBuilder.group({
      roleNombre: this.formBuilder.control('', [Validators.maxLength(50),Validators.minLength(3)]),


    });


  }


}

