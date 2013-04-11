/**
 * Services.alacarta Package
 */

(function() {

var alacarta = new afrous.UnitActionPackage('Services.Alacarta', {
  label : 'alacarta',
});

alacarta.register(new afrous.UnitAction({
  type : 'Noticias',
  label : 'Extraer noticias',
  description : 'Devuelve las noticias de la URL introducida por el usuario.',
  inputs : [
    { name : 'url_entrada',
      label : 'URL',
      type : 'String' },
  ]
  ,
  execute : function(request, callback) {
    if (request.params['url_entrada']) {
/*      var url = 'http://localhost:3000/extractors/json?url=' + request.params['url_entrada']; */	
        var url = 'http://localhost:3434/ejson/' + request.params['url_entrada'];
/*       afrous.ajax.jsonp.invoke(url, callback); */
        callback2 = {onSuccess : function(value) {	

          /* List of predicates */
          var predicates = [];
          for (i=0;i<value.length;i++){
            for (var j in value[i]) {
              var check=false;
              for (p=0; p<predicates.length;p++) {
                if (predicates[p]==j) check=true;
              }
              if (!check) predicates.push(j);
            }
          }
	

          /* Adding all predicates */  
          for (i=0;i<value.length;i++) {
            for (p=0; p<predicates.length;p++) {
                var check = false;
                for (var j in value[i]) {
                    if (j==predicates[p]) check=true;  		        
                }
                if (!check) value[i][predicates[p]] = "unknown";		
            }
          }
	
          
          /* Multiple values */
          for (i=0;i<value.length; i++) {
            for (var j in value[i]) {
              if (j=="http://www.daml.org/experiment/ontology/location-ont#location") {
                var valor =value[i][j][0]["http://www.w3.org/1999/02/22-rdf-syntax-ns#label"]			
                if (valor==null) value[i][j] = "unknown"		
                else value[i][j] = valor;
              }
		
              if (Object.prototype.toString.call(value[i][j]) == '[object Array]') {               
                value[i][j] = value[i][j].join(", ");		      
              }
	      if (j=="http://purl.org/dc/elements/1.1/category"){
		var oldValue = value[i][j];		

		transformations = {"España":"Nacional","Barcelona":"Nacional","Madrid":"Nacional","Andalucía":"Nacional","Baleares":"Nacional",
				"Castilla y León":"Nacional","C. Valenciana":"Nacional","Galicia":"Nacional","País Vasco":"Nacional","Lleida":"Nacional",
				"Valencia":"Nacional","Bilbao":"Nacional","Málaga":"Nacional","Sevilla":"Nacional","Mallorca":"Nacional",
				"Zaragoza":"Nacional","Ed. Regionales":"Nacional","Unión Europea":"Internacional","Mundo":"Internacional",
				"Europa":"Internacional","La Vida":"Internacional","Deporte":"Deportes","Fútbol":"Deportes","Fórmula 1":"Deportes",
				"Motor":"Deportes","NBA":"Deportes","Baloncesto":"Deportes","Tenis":"Deportes","Motos":"Deportes",
				"Vela":"Deportes","Cine":"Cultura","Espectáculos":"Cultura","Libros":"Cultura","Música":"Cultura","Toros":"Cultura",
				"Arte":"Cultura","Pintura":"Cultura","Teatro":"Cultura","Cultura & Ocio":"Cultura",
				"Belleza/Salud":"Salud y Medio Ambiente","A Tu Salud":"Salud y Medio Ambiente",
				"Verde":"Salud y Medio Ambiente","Salud":"Salud y Medio Ambiente","Belleza":"Salud y Medio Ambiente",
				"Ciencia/Tecnología":"Ciencia y Tecnología","Tecnología":"Ciencia y Tecnología","Ciencia":"Ciencia y Tecnología",
				"La Red":"Ciencia y Tecnología","Navegante":"Ciencia y Tecnología","Medios":"Ciencia y Tecnología",
				"Gente y TV":"Gente","Moda":"Gente","Vivienda":"Economía","Comunicación TV":"Televisión","Programas":"Televisión",
				"Series":"Televisión","Televisión & Radio":"Televisión","VD Viajes":"Viajes","Asia":"Internacional",
				"América":"Internacional","Metrópoli":"Nacional","Exteriores":"Internacional",
				"Novedades del Sector":"Ciencia y Tecnología","Actualidad Turística":"Viajes",
				"Noticias \"La Red\"":"Ciencia y Tecnología","Sucesos":"Nacional",
				"Noticias curiosas":"Nacional","Medicina y Sanidad":"Salud y Medio Ambiente",
				"Reporter":"Blogs y reportages","Defensa":"Nacional","Blog":"Blogs y reportages",
				"Medio Ambiente y Biodiversidad":"Salud y Medio Ambiente","Cataluña":"Nacional","África":"Internacional",
				"Política":"Nacional","Meteorología":"El Tiempo","Murcia":"Nacional","Coyuntura":"Nacional",
				"Desarrollo Sostenible":"Salud y Medio Ambiente","Religion":"Nacional","Local":"Nacional","Medios y Redes":"Televisión",
				"Columnas":"Blogs y Reportajes"}
		 		
		if (transformations[value[i][j]] == undefined){
			value[i][j] = oldValue;			
		}else{		
       		value[i][j] = transformations[value[i][j]];				
		}
	      }
	     if (j=="http://purl.org/dc/elements/1.1/comment"){
		var firstValue = value[i][j];
		if (value[i][j] == "comentarios"){
			value[i][j] = 0;
		}else{
			value[i][j] = firstValue;
		}
	     }
            }        
          }
          callback.onSuccess(value);
        },
        onFailure : function(error) {
          alert("Failure");
          callback.onFailure(error);
        }
        }
        afrous.ajax.jsonp.invoke(url, callback2);
    } else {
      alert("Introduce URL");
      callback.onFailure();
    }
  }

}));

afrous.packages.register(alacarta, 'alacarta.js');

})();
