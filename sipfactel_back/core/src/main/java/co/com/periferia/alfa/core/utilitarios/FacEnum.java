package co.com.periferia.alfa.core.utilitarios;

public enum FacEnum {

	//enums de la clase utilitaria seccion
	
	INVOICEAUTHORIZATION("InvoiceAuthorization"),
	STARTDATE("StartDate"),
	ENDDATE("EndDate"),
	PREFIX("Prefix"),
	FROM("From"),
	TO("To"),
	IDENTIFICATIONCODE("IdentificationCode"),
	PROVIDERID("ProviderID"),
	PROVIDERID_SCHEMAID("ProviderID_schemeID"),
	SOFTWAREID("SoftwareID"),
	SOFTWARESECURITYCODE("SoftwareSecurityCode"),
	AUTHORIZATIONPROVIDERID("AuthorizationProviderID"),
	AUTHORIZATIONPROVIDERID_SCHEMEID("AuthorizationProviderID_schemeID"),
	QRCODE("QRCode"),

	PARTECIPATIONPERCENT("PartecipationPercent"),
	
	UBLVERSIONID("UBLVersionID"),
	CUSTOMIZATIONID("CustomizationID"),
	PROFILEID("ProfileID"),
	PROFILEEXECUTIONID("ProfileExecutionID"),
	ID("ID"),
	UUID("UUID"),
	ISSUEDATE("IssueDate"),
	ISSUETIME("IssueTime"),
	INVOICETYPECODE("InvoiceTypeCode"),
	DOCUMENTCURRENCYCODE("DocumentCurrencyCode"),
	LINECOUNTNUMERIC("LineCountNumeric"),
	CREDITNOTETYPECODE("CreditNoteTypeCode"),
	
	LINEEXTENSIONAMOUNT("LineExtensionAmount"),
	TAXEXCLUSIVEAMOUNT("TaxExclusiveAmount"),
	TAXINCLUSIVEAMOUNT("TaxInclusiveAmount"),
	PAYABLEAMOUNT("PayableAmount"),
	
	CUSTOMERAASSIGNEDACCOUNTID("CustomerAssignedAccountID"),
	ADDITIONALACCOUNTID("AdditionalAccountID"),
	PARTYNAME("PartyName"),
	PHYSICAL_ADD_ID("Physical_ADD_ID"),
	TAX_REGISTRATIONNAME("Tax_RegistrationName"),
	TAX_COMPANYID("Tax_CompanyID"),
	TAX_COMPANYID_SCHEMEID("Tax_CompanyID_schemeID"),
	TAX_COMPANYID_SCHEMENAME("Tax_CompanyID_schemeName"),
	TAX_LEVELCODE("Tax_LevelCode"),
	TAX_LEVELCODE_LISTNAME("Tax_LevelCode_listName"),
	TAX_SCHEME_ID("Tax_Scheme_ID"),
	TAX_SCHEME_NAME("Tax_Scheme_Name"),
	REGISTRATION_ADD_ID("Registration_ADD_ID"),
	PAYMENTMEANSCODE("PaymentMeansCode"),
	PAYMENTDUEDATE("PaymentDueDate"),
	INSTRUCTIONNOTE("InstructionNote"),
	PAYMENTID("PaymentID"),
	
	TAXAMOUNT("TaxAmount"),
	SCHEMEID("SchemeID"),
	SCHEMENAME("SchemeName"),
	TAXSUBTOTAL1_TAXABLEAMOUNT("TaxSubTotal1_TaxableAmount"),
	TAXSUBTOTAL1_TAXAMOUNT("TaxSubTotal1_TaxAmount"),
	TAXSUBTOTAL1_PERCENT("TaxSubTotal1_Percent"),
	
	INVOICEDQUANTITY("InvoicedQuantity"),
	INVOICEDQUANTITYUNITCODE("InvoicedQuantityUnitCode"),
	PRICEAMOUNT("PriceAmount"),
	BASEQUANTITY("BaseQuantity"),
	BASEQUANTITY_UNITCODE("BaseQuantity_unitCode"),
	ITEM_DESCRIPTION("Item_Description"),
	STANDARD_ITEMID("Standard_ItemID"),
	STANDARD_ITEMID_SCHEMEID("Standard_ItemID_SchemeID"),
	TAXSUBTOTAL_TAXABLEAMOUNT("TaxSubTotal_TaxableAmount"),
	TAXSUBTOTAL_TAXAMOUNT("TaxSubTotal_TaxAmount"),
	TAXSUBTOTAL_PERCENT("TaxSubTotal_Percent"),
	
	DEBITEDQUANTITY("DebitedQuantity"),
	DEBITEDQUANTITYUNITCODE("DebitedQuantityUnitCode"),
	CREDITEDQUANTITY("CreditedQuantity"),
	CREDITEDQUANTITYUNITCODE("CreditedQuantityUnitCode"),
	
	NOMBRE("Nombre"),
	EMAIL("Email"),
	ENVIAR_EMAIL("Enviar_Email"),
	INCLUIR_ANEXOS("Incluir_Anexos"),
	INCLUIR_PDF("Incluir_PDF"),
	INCLUIR_XML("Incluir_XML"),
	CITYID("CityID"),
	CITYNAME("CityName"),
	POSTALZONE("PostalZone"),
	COUNTRYSUBENTITY("CountrySubentity"),
	COUNTRYSUBENTITYCODE("CountrySubentityCode"),
	ADDRESSLINE("AddressLine"),
	COUNTRYNAME("CountryName"),
	COUNTRYCODE("CountryCode"),
	INVOICE_ID("Invoice_ID"),
	INVOICE_UUID("Invoice_UUID"),
	INVOICE_ISSUEDATE("Invoice_IssueDate"),
	CODIGO_COLOMBIA("CO"),
	CODIGO_ARGENTINA("AR"),

	CONTACT_ID("Contact_ID"),
	CONTACT_NAME("Name"),
	CONTACT_EMAIL("ElectronicMail"),
	
	EXT("EXT"),
	FAC("FAC"),
	TOT("TOT"),
	ASP("ASP"),
	ACP("ACP"),
	PYM("PYM"),
	TXT("TXT"),
	REC("REC"),
	ADD("ADD"),
	DEB("DEB"),
	BRF("BRF"),
	CRE("CRE"),
	CON("CON"),
	ASS("ASS"),
	NOT("NOT"),
	LIT("LIT"),
	DNL("DNL"),
	CNL("CNL"),
	IVL("IVL"),
	
	// enums generales del proyecto
	FORMATO_FECHA1("yyyy/MM/dd"),
	FORMATO_FECHA2("yyyy-MM-dd"),
	FORMATO_FECHA3("dd/MM/yyyy"),
	APLICACION("Aplicacion"),
	DOCUMENT("Document"),
	TR_TIPO_ID("tr_tipo_id"),
	TOKEN("token"),
	PAGE("page"),
	DATA("data"),
	ERROR_BASE_64("ERROR convirtiendo json a Base 64"),
	DOCUMENTOS("documentos"),
	ENVDOCS("doc_id"),
	BUSQUEDA("busqueda"),
	TRANSACCION_ID("transaccion_id"),
	CAMPO("campo"),
	COMPARACION("comparacion"),
	VALOR("valor"),
	TRANSACCION("transaccion"),
	WHITE_SPACE(" "),
	NOTWHITE_SPACE(""),
	LINE("-"),
	SLASH("/"),
	PUNTO0(".0"),
	ID_DATOS_DELCOP("1"),
	DEBITO("debito"),
	CREDITO("credito"),
	FACTURA("factura"),
	
	FECHAINI("FECHAINI"),
	FECHAFIN("FECHAFIN"),
	TIPODOC("TIPODOC"),
	NUMDOC("NUMDOC"),
	NUMPOLIZA("NUMPOLIZA"),
	ESTADO("ESTADO"),
	COMPANIA("COMPANIA"),
	SUCURSAL("SUCURSAL"),
	ID_TIPO("ID_TIPO"),
	CATEGORIA("CATEGORIA"),
	IDTABLA("IDTABLA"),
	TABLA("TABLA"),
	TIPOMOV("TIPOMOV"),
	FACT("FACT"),
	DETFAC("DETFAC"),
	DESCRIPCIONSERVICIO("DESCRIPCIONSERVICIO"),
	VALORSERVICIO("VALORSERVICIO"),
	VALORBRUTO("VALORBRUTO"),
	TARIFA("TARIFA"),
	BASEIMPONIBLE("BASEIMPONIBLE"),
	VALORTRIBUTO1("VALORTRIBUTO1"),
	TOTALFACTURA("TOTALFACTURA"),
	POL("POL"),
	FINIENVIO("FINIENVIO"),
	FFINENVIO("FFINENVIO"),
	FINIEMISION("FINIEMISION"),
	FFINEMISION("FFINEMISION"),
	
	TRUE("TRUE"),
	FALSE("FALSE"),
	REGISTRO_GUARDADO("El registro fue guardado correctamente"),
	
	HORAENVIO_DEFECTO("0 0/30 * * * ?"),
	
	TIPOIDENTIFICACION_NIT("31"),
	
	COMA(","),
	
	DATE_FORMAT("yyyy-MM-dd"),
	DATE_FORMAT2("dd/MM/yy"),
	ZONA_HORARIA("America/Bogota"),
	
	STRING_PRUEBA("a"),
	
	//datos para consultar en parametros
	LIMITE_DEBITO("LIMITE_DEBITO"),
	LIMITE_CREDITO("LIMITE_CREDITO"),
	LIMITE_FACTURA("LIMITE_FACTURA"),
	COD_PERSONA_JURIDICA("COD_PERSONA_JURIDICA"),
	LIMITE_ADQUIRIENTES("LIMITE_ADQUIRIENTES"),
	CONSECUTIVO_FE("CONSECUTIVO_FE"),
	CONSECUTIVO_NOTAS("CONSECUTIVO_NOTAS"),
	DIAS_RESTANTES_VENCIMIENTO_RESOLUCION("DIAS_RESTANTES_VENCIMIENTO_RESOLUCION"),
	
	//valores por defecto para el limite de facturas y notas
	LIMITE_FACTURADEFECTO("50"),	
    LIMITE_NOTAS("25"),
	
	//mensaje de sescion caudacad
	SESION_CADUCADA("Su sesi\u00f3n ha caducado debe iniciar sesi\u00f3n nuevamente."),
	
	//valores por defecto para el limite de facturas y notas
	COASEGURADORA("COASEGURADORA"),
	TDOC("TDOC"),
	NDOC("NDOC"),
	PORC_PART("PORC_PART"),
	NUMPOL("NUMPOL"),
	RAMO("RAMO"),
    NOTE("Note"),
	
	//Valores de tipos de polizas
	GENERAL("GENERAL"),
	PREVISIONAL("PREVISIONAL"),
	ARL("ARL"),
	VITALICIA("VITALICIA");
	
	private String valor;
	
	private FacEnum (String valor) {
		this.valor = valor;
	}
	
	public String getValor () {
		return valor;
	}
	
}
