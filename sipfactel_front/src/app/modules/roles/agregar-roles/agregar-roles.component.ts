import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

//#region  servicios
import { RolesService } from '../../../services/roles.service';
//#endregion servicios

//#region rutas
import { Router } from '@angular/router';
////#endregion tutas

//#region  modelos
import { agregar } from '../../../models/rolesAgregar';
import { menus } from '../../../models/menusRol';
import { permisos } from 'src/app/models/permisos';
///#endregion modelos

//#region  bibliotecas
import { HttpErrorResponse } from '@angular/common/http';
import Swal from 'sweetalert2';
import { menusHijos } from '../../../models/menusHijosRoles';
import { ThrowStmt } from '@angular/compiler';
import { Categoria } from 'src/app/models/Polizas/categoria';
import { selectInicial } from '../../../directives/validators/select.validator';
//#endregion bibliotecas

@Component({
  selector: 'app-agregar-roles',
  templateUrl: './agregar-roles.component.html',
})
export class AgregarRolesComponent implements OnInit {
    ;
  subpermisos = [{ id: 1, valor: 0, nombre: 'crear' }, { id: 2, valor: 0, nombre: 'modificar' }, { id: 3, valor: 0, nombre: 'consultar' }
  ]
  @Output() agrego = new EventEmitter<boolean>();
  public permisos: menus[] = [];
  public permisoHijos: permisos
  public Roles: agregar;
  public permisoMenu: menus;

  public role: permisos;
  public menusHijos : menusHijos []=[]
  public hijosNull : menus []=[]
  public frmAgregarRoles: FormGroup;
  public cat: Categoria [] = []

  public resultado: string;
  public roleEsta: string

  public esRolesValido: boolean;
  constructor(
    private rolesService: RolesService,
    public formBuilder: FormBuilder,
    private router: Router,
  ) {
    this.Roles = new agregar();
    console.log('categoria inicial',this.Roles.categoria)
    this.permisoMenu = new menus();
  }
  ngOnInit() {
    this.ObtenerModulos();
    this.validaciones();
    this. obtenerCategorias()
    this.esRolesValido = false;
    this.roleEsta = "ACT";
    this.Roles.roleEsta = this.roleEsta;
    
  }
  obtenerCategorias(){

    this.rolesService.categorias().subscribe((res : any ) => {
      
      this.cat = res.resultado
      
    })
  }

  
  agregarRoles() { // Servicio de  agregar un nuevo rol
    this.asignarHijoAPadre()
    this.rolesService
    .crearRoles(this.Roles)
    .subscribe(
      (res: any) => { // Solicitud completada con éxito o fallo
        
        Swal.fire({
          type: 'success',
            title: 'Operación Exitosa',
            text: res.mensajeEstado,
          });
          this.agrego.emit(false);
          this.router.navigate(['/Roles/home']);
        },
        (error: HttpErrorResponse) => { }
        );
      }
      
      

      

  comprobarValideRoles() { // Valida que minimo un permiso este seleccionado
    
    if (this.esRolesValido = this.Roles.modulos.length > 0) {
      this.esRolesValido = true
    }
  }
  
  esRolSeleccionado(rolId: string) { // Verifica los permisos seleccionados
    
    const esSeleccionado = this.Roles.modulos.some((role: permisos) => {
      return role.idMenu.toString() === rolId.toString();
    });
    return esSeleccionado;

  }
  
  

  Permisos(event: Event, idMenu: number, menuMenu: string, consultar: number, modificar: number, crear: number) { // función de los checkbox
    
    const eventTarget = event.target as HTMLInputElement;

    if (eventTarget.checked) { // Valida que la informacion del checkbox obtenida tenga los mismos paramtros del modelo
      this.role = new permisos(idMenu, menuMenu, consultar, modificar, crear);
      
      this.Roles.modulos.push(this.role);
    }
    else {

      this.Roles.modulos = this.Roles.modulos.filter((role: permisos) => { // Elimina el array cuando se quita un check
        return role.idMenu.toString() !== idMenu.toString();
        
      });
    }
    this.comprobarValideRoles();
    
  }
  
  
  subPermisos(idMenu: number, nombre: string, menuMenu: string, consultar: number, modificar: number, crear: number) { // Fución de los checkbox internos de los permisos
    
    const eventTarget = event.target as HTMLInputElement;

    if (eventTarget.checked) { // Valida  que el check seleccionado  que sea propio del id del permiso y le da un nuevo valor

      if (nombre === "crear" && idMenu === idMenu) {
        
        crear = 1
        this.Roles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.crear = crear
          } else (item)
        })
        
      } else if (nombre === "modificar" && idMenu === idMenu) {
        
        modificar = 1
        
        this.Roles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.modificar = modificar
          } (item)
          
        })
        
      } else if (nombre === "consultar") {
        
        consultar = 1
        this.Roles.modulos.forEach(item => {
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
        this.Roles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.crear = crear
          } else (item)
          
        })

      } else if (nombre == 'modificar') {
        modificar = 0
        this.Roles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            
            item.modificar = modificar
          } else (item)
          
        })
        
      } else if (nombre == 'consultar') {
        consultar = 0
        this.Roles.modulos.forEach(item => {
          if (item.idMenu === idMenu) {
            item.consultar = consultar
          } else (item)
          
          
        })
        
      }
      
    }
    
  }
  
  asignarHijoAPadre(){
  
    
    
    this.permisos.map(datos => {
      
      datos.hijos.map(hijoEstandar => {
        this.Roles.modulos.map(hijoEntrante =>{
          
          
          if(hijoEntrante.idMenu === hijoEstandar.idMenu){
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
      this.Roles.modulos.map(data => {

        if(data.idMenu === 7 || data.idMenu === 2 || data.idMenu === 1  || data.idMenu === 17 || data.idMenu === 18){
          this.hijosNull.push(data)
        }}
        )
        
        this.Roles.modulos = []
        
        this.NuevosHijos()
      }
      
      NuevosHijos(){
        
        
        
        this.permisos.map(datas => {
          if(datas.consultar == 1 || datas.crear == 1 || datas.modificar == 1)
        { 
          datas.consultar = 0
          datas.crear = 0
          datas.modificar = 0
          this.Roles.modulos.push(datas)
         
        }
      })
      this.padres()
  }

  padres(){
    if(this.hijosNull.length > 0){
      this.hijosNull.map(padre => {
        
        this.Roles.modulos.push(padre)
      })
      
    }
  }
  ObtenerModulos() { // Servicio que lista los permisos 
    
    this.rolesService.listarMenus().subscribe((res: any) => { // Solicitud con exitó o fallo
      this.resultado = JSON.stringify(res);
      this.permisos = res.resultado;
      this.permisos.forEach(item => {
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
    
    
    validaciones() { // Valida los campos requeridos y su longitud
      this.frmAgregarRoles = this.formBuilder.group({
      roleNombre: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      roleDescripcion: this.formBuilder.control('', [Validators.required, Validators.maxLength(100)]),
      cat: this.formBuilder.control(0, [Validators.required,selectInicial]),
      roleEsta: this.formBuilder.control(0, [Validators.required,selectInicial]),


    });
  }

  cancelar (){

    this.router.navigate(['/Roles/home'])
  }

}
