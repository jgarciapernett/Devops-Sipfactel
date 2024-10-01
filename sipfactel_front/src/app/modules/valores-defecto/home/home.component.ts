import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { selectInicial } from '../../../directives/validators/select.validator';
import { Router } from '@angular/router';
import { datosA } from '../../../models/adquirientes/datosAdministrables';
import { AdquirientesService } from '../../../services/adquirientes.service';
import { Lista } from 'src/app/models/lista';
import { timeStamp } from 'console';
import Swal from 'sweetalert2';
import { runInThisContext } from 'vm';
import { codigoPostal } from 'src/app/models/sucursales/codigoPostal';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  public frmEditarDatosGenerales: FormGroup;
  public frmEditarDatos: FormGroup;
  public frmEditarDatosGeneralesform : FormGroup
  public formularioAdquiriente1: boolean;
  public formularioAdquiriente2: boolean;
  public formularioAdquiriente3: boolean;
  public formUno: boolean;
  public formDos: boolean;
  public formTres: boolean;
  public datos: datosA [] =[]
  public datosEnvio: datosA
  public obligacion : Lista;

  public listaCiudades: Lista[];
  public regimenes: Lista[];
  public codPostal: codigoPostal[];
  public listaDepartamentos: Lista[];
  public FacElectronica: Lista[];
  public listaFacElectronica: Lista[] = [];
  public Tributo: Lista[] = [];
  public listaobligacionesFiscales: Lista[];
  public esObligacionValido: boolean;
  public esObligacion: Lista;


   public cciudad : number
   public cpost : number
   public cregimen : number
   public cdepar : number
   public razon : string
   public direcc : string
   public contac : string 
   public tel : number
   public area:string
   public fac:number
   public email:string
   public tribu : number
  public id : number
  esCrear: boolean;
  esConsultar: boolean;
  esEditar: boolean;



  constructor(
    private datosa : AdquirientesService,
    public formGroup: FormBuilder,
    private router: Router,

  ) {
    this.formularioAdquiriente2 = false;
    this.formularioAdquiriente3 = false;
    this.formularioAdquiriente1 = true;
    this.Ciudades()
    this.Departamentos()
    this.obtenerCodigoPostal()
    this.Tributos()
    this.obtenerRegimenFiscal()
    this.esObligacionValido = false
    this.datos.map(dato => {
      this.datosEnvio = dato
    })

    this.ObtenerPermisos('valores')
    this.obtenerObligacionFiscal()
    
  }
  
  
  ngOnInit() {
    this.validacionesFormulario()
    this.validacionesFormulario2()
    this.validacionesFormulario3()
    this.formUno = true;
    this.obtenerDatosA()
    
  }
 
  esObligacionSeleccionada(rolId: string) { // Verifica los permisos seleccionados
    
    const esSeleccionado = this.datosEnvio.obligacionesfiscales.some((role: Lista) => {
      this. comprobarValideObligacion()
      return role.id.toString() === rolId.toString();
    });
    return esSeleccionado;

  }
  comprobarValideObligacion() { // Valida que minimo un permiso este seleccionado

    if (this.esObligacionValido = this.datosEnvio.obligacionesfiscales.length > 0) {
      this.esObligacionValido = true
    }
    else(
      this.esObligacionValido = false
    )

    }




  Obligaciones(event: Event, idMenu: number, menuMenu: string, idstr: string,) { // función de los checkbox

    const eventTarget = event.target as HTMLInputElement;

    if (eventTarget.checked) { // Valida que la informacion del checkbox obtenida tenga los mismos paramtros del modelo
      this.obligacion = new Lista(idMenu, menuMenu, idstr,);
      this.datosEnvio.obligacionesfiscales.push(this.obligacion)
    


      
    }
    else {

      this.datosEnvio.obligacionesfiscales = this.datosEnvio.obligacionesfiscales.filter((role: Lista) => { // Elimina el array cuando se quita un check
        return role.id.toString() !== idMenu.toString();

      });
    }
    this.comprobarValideObligacion();

  }

  obtenerObligacionFiscal(){
    
    this.datosa.listaObligacionesFiscales().subscribe((res: any)=>{
      this.listaobligacionesFiscales = res.resultado;
    });
  }

  ObtenerPermisos(menuAlias: string) { // Servicio que da permisos al usuario dependiendo del rol que 
    
    var objsesion = JSON.parse(localStorage.getItem('sesion'));
    var menu = objsesion.listMenu;
    menu.forEach(item => { // Mapeo de los modulos y sus respectivos permisos
        if( item.menuAlias === menuAlias){
          this.esCrear = item.crear;
          this.esConsultar = item.consultar;
          this.esEditar = item.modificar;
        }
    
  
    });
  


  }
  obtenerDatosA(){
    
    this.datosa.obtenerDatosAdministrables().subscribe((res:any) =>{
      this.datos = res.resultado 
      this.datosEnvio = res.resultado
      // if(this.datosEnvio.obligacionesfiscales === undefined ){
      //   this.datosEnvio.obligacionesfiscales = []
      // }
      this.datos.map(dato => {
        this.datosEnvio.obligacionesfiscales = dato.obligacionesfiscales
        this.cpost  = dato.post
        this.razon = dato.nombreRazonSocial
      })
    }
      )
      
    }
    
    
    detalleFormulario1() {
      this.formularioAdquiriente1 = true;
      this.formularioAdquiriente2 = false;
      this.formularioAdquiriente3 = false;
      this.formUno = true;
      this.formDos = false;
    this.formTres = false;
    
  }
  siguienteAdquiriente() {
    if (this.formularioAdquiriente1 = true) {
      this.formularioAdquiriente1 = false;
      this.formularioAdquiriente2 = true;
      this.formularioAdquiriente3 = false;
      this.formUno = false;
      this.formDos = true;
      this.formTres = false;
      this.datos.map(dato => {
        
        this.direcc   = dato.direccionFisica
        this.contac  = dato.contacto
        this.tel = dato.telefono
        this.area = dato.areaPertenece 
        this.fac = dato.facturadorElectronico
        this.email = dato.correo
        this.cciudad   = dato.codCiudad
        this.cdepar  = dato.codDepartamento
        this.cregimen = dato.codRegimen 
       this.tribu  = dato.codTipoTributo


      })
    }
    
  }
  siguienteFactura() {
    
    if (this.formularioAdquiriente2 = true) {
      this.formularioAdquiriente1 = false;
      this.formularioAdquiriente2 = false;
      this.formularioAdquiriente3 = true;
      this.formUno = false;
      this.formDos = false;
      this.formTres = true;
      this.datos.map(dato => {
        
       this.tribu  = dato.codTipoTributo
       this.cregimen = dato.codRegimen
        
      })
    
      
    }
  }
  atrasFormulario1() {
    if (this.formDos = true) {
      this.formularioAdquiriente1 = true;
      this.formularioAdquiriente2 = false;
      this.formularioAdquiriente3 = false;
      this.formUno = true;
      this.formDos = false;
      this.formTres = false;
    }
  }
  detalleFormulario2() {

    
    this.formularioAdquiriente1 = false;
    this.formularioAdquiriente2 = true;
    this.formularioAdquiriente3 = false;
    this.formUno = false;
    this.formDos = true;
    this.formTres = false;
    
  }
  
  detalleFactura() {
    this.formularioAdquiriente1 = false;
    this.formularioAdquiriente2 = false;
    this.formularioAdquiriente3 = true;
    this.formUno = false;
    this.formDos = false;
    this.formTres = true;
  }
  botonCancelar() {// Funcion de regreso a la pantalla home
    this.router.navigate(['/obligatorios/home']);
  }
  
  
  Ciudades() {
    this.datosa.obtenerListaCiudades().subscribe((resp: any) => {
      this.listaCiudades = resp.resultado;
    });
  }
  
  Departamentos() {
    this.datosa
    .obtenerListaDepartamentos()
    .subscribe((resp: any) => {
      this.listaDepartamentos = resp.resultado;
      // console.log('resp', resp);
    });
  }
  
  obtenerRegimenFiscal() {
    this.datosa.obtenerRegimenFiscal().subscribe((res: any) => {
      this.regimenes = res.resultado;
    });
  }
  
  obtenerCodigoPostal() {
    this.datosa.obtenerCodigoPostal().subscribe((res: any) => {
      this.codPostal = res.resultado;
    });
  }


  FacturaElectronica() {
    this.datosa
    .obtenerFacturaElectronica()
    .subscribe((resp: any) => {
      this.listaFacElectronica = resp.resultado;
    });
  }
  
  Tributos() {
    this.datosa.obtenerTributo().subscribe((resp: any) => {
      this.Tributo = resp.resultado;
    });
  }

  asignarDatosEditados(){
    this.datos.map(dato => {
      this.id = dato.vad
      dato.obligacionesfiscales = this.datosEnvio.obligacionesfiscales
      dato.areaPertenece = this.area
      dato.nombreRazonSocial =this.razon
      dato.codCiudad =this.cciudad
      dato.codDepartamento =this.cdepar
      dato.post =this. cpost
      dato.direccionFisica =this.direcc
      dato.contacto =this.contac
      dato.correo =this.email
      dato.telefono =this.tel
      dato.facturadorElectronico =this.fac
      dato.codTipoTributo =this.tribu
      dato.codRegimen =this.cregimen
    })
    this.datos.map(dato => {
      this.datosEnvio = dato
    })
  }
  
  editarCampos() {

    
    this.asignarDatosEditados()
    this.datosa.editarDatos(this.datosEnvio, this.id).
      subscribe((res: any) => {// Solicitud completada con éxito o fallo
        

          Swal.fire({
            type: 'success',
            title: 'Operación Exitosa',
            showConfirmButton: true,
            confirmButtonColor: '#149275', 
            text: res.resultado
          }).then(resp =>{   // Validacion de eliminar Si se preciona el boton OK se consume el servicio y se elimina el registro
            if (resp.value)
            {  
              window.location.reload()
              }

          
            })
     
  })

}

  
  
  validacionesFormulario() {
    this.frmEditarDatosGenerales = this.formGroup.group({
      Razon: this.formGroup.control('', [Validators.required,Validators.maxLength(200)]),
      
      
    });
  }

  
  validacionesFormulario2() {
    this.frmEditarDatos = this.formGroup.group({
      Postal: this.formGroup.control(0, [Validators.required,selectInicial]),
      IDCiudad: this.formGroup.control(0, [Validators.required,selectInicial]),
      Departamento: this.formGroup.control(0, [Validators.required,selectInicial]),
      // CodDepartamento: this.formGroup.control(0, [Validators.required,selectInicial]),
      Dirección: this.formGroup.control('', [Validators.required,Validators.maxLength(200)]),
      NomContac: this.formGroup.control('', [Validators.required,Validators.maxLength(200)]),
      Email: this.formGroup.control('', [Validators.pattern("[a-z0-9._%+-]+@[a-z0-9.+-]+\.[a-z]{2,3}$"),Validators.maxLength(200)]),
      Telefono: this.formGroup.control('', [Validators.required,]),
      AreaP: this.formGroup.control('', [Validators.required,Validators.maxLength(200)] ),
      // FacElec: this.formGroup.control(0, [Validators.required,selectInicial]),
    });
  }
  validacionesFormulario3() {
    this.frmEditarDatosGeneralesform = this.formGroup.group({
      fac: this.formGroup.control(2, [selectInicial]),
      txtTributo: this.formGroup.control(0, [Validators.required, selectInicial]),
      txtRegimen: this.formGroup.control(0, [Validators.required, selectInicial]),
    });
  

  }

}
