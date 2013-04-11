/*graphics.js 
* It creates different types of diagrams 
*/

(function() {
var graphics = new afrous.UnitActionPackage('Renderer.Diagram', {        
  label : 'Diagram'
});

graphics.register(new afrous.RenderingUnitAction({
  type : 'PieOldExtractor',
  label : 'Pie Diagram with authors- old extractor ',
  description : 'Pie Diagram',
  iconCls : 'chart-pie-uaction',
  inputs : [
    { name : 'records',
      label : 'input records',
      type : 'Object[]' },
    { name : 'width',
      label : 'width',
      type : 'Integer' },
    { name : 'height',
      label : 'height',
      type : 'Integer' },
    { name : 'colorScheme',
      label : 'color scheme',
      type : 'String',
      options : [ 'blue', 'darkcyan', 'green', 'red', 'grey', 'black' ] }
  ],      
	render : function(params, el) {

    var records = params['records'];  
    if(!records) return;
    
    var width =  400;
    if( params['width'] != null ){
      width = params['width'];
    }       
    
    var height = 400;
    if( params['height'] != null ){
      height = params['height'];
    }
                      
    var colorScheme = 'blue';
    if( params['colorScheme'] != null ){
      colorScheme =  params['colorScheme'];
    }              

    var options = {
        padding: {
            left: 30, 
            right: 30, 
            top: 30, 
            bottom: 30
                   },	
				
	
         background: {
		      color: '#ffffff'
	                 },					
	       colorScheme: colorScheme,				
	       axis: {
		     //Color de las etiquetas: negro
		     lineWidth:			1.0,
				lineColor:			'#000000',
				tickSize:			7.0,

				labelFont:			'Tahoma',
				labelFontSize:		10,
				labelWidth: 		70.0,
		    labelColor: '#000000',
		    
    		x: {
		  	ticks: [
				{v:0, label:'Agencia EFE'}, 
		      	{v:1, label:'Otros autores'},
			]
      		  }	
	       }
    };  
       
       
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
                                
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartOldExtractor(doc, records, width, height, options); 
   doc.close(); 
   }
  }
  
}))



graphics.register(new afrous.RenderingUnitAction({
  type : 'PieNewExtractor',
  label : 'Pie Diagram with authors - scrappy',
  description : 'Pie Diagram - scrappy',
  iconCls : 'chart-pie-uaction',
  inputs : [
    { name : 'records',
      label : 'input records',
      type : 'Object[]' },
    { name : 'width',
      label : 'width',
      type : 'Integer' },
    { name : 'height',
      label : 'height',
      type : 'Integer' },
    { name : 'colorScheme',
      label : 'color scheme',
      type : 'String',
      options : [ 'blue', 'darkcyan', 'green', 'red', 'grey', 'black' ] }
  ],  
	render : function(params, el) {
	  /*
	  Valores predeterminados de la grafica:
	  width = 500
	  height = 500
	  colorScheme = blue	  
	  */	  
  	var records = params['records'];  
    if(!records) return;
    
    var width =  400;
    if( params['width'] != null ){
      width = params['width'];
    }       
    
    var height = 400;
    if( params['height'] != null ){
      height = params['height'];
    }
                      
    var colorScheme = 'blue';
    if( params['colorScheme'] != null ){
      colorScheme =  params['colorScheme'];
    }              
            
                
	  //Opciones que definen las caracteristicas de la grafica
    var options = {
      	padding: {
		    left: 30, 
		    right: 30, 
		    top: 30, 
		    bottom: 30
	               },	
				
	
	     background: {
		      color: '#ffffff'
	                 },					
	       colorScheme: colorScheme,				
	       axis: {
		     //Color de las etiquetas: negro
		     lineWidth:			1.0,
				lineColor:			'#000000',
				tickSize:			7.0,

				labelFont:			'Tahoma',
				labelFontSize:		10,
				labelWidth: 		70.0,
		    labelColor: '#000000',
		    
    		x: {
		  	ticks: [
				{v:0, label:'Agencia EFE'}, 
		      	{v:1, label:'Otros autores'}, 
			]
      		  }	
	       }
    };        
       
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
                                
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartNewExtractor(doc, records, width, height, options); 
   doc.close(); 
   }
  }
  
}))


graphics.register(new afrous.RenderingUnitAction({
  type : 'BarChartComments',
  label : 'Bar Diagram - comments',
  description : 'Bar Diagram - comments',
  iconCls : 'chart-pie-uaction',
  inputs : [
    { name : 'records',
      label : 'input records',
      type : 'Object[]' },
    { name : 'width',
      label : 'width',
      type : 'Integer' },
    { name : 'height',
      label : 'height',
      type : 'Integer' },
    { name : 'colorScheme',
      label : 'color scheme',
      type : 'String',
      options : [ 'blue', 'darkcyan', 'green', 'red', 'grey', 'black' ] }
  ],  
	render : function(params, el) {

 	var records = params['records'];  
    if(!records) return;
    
    var width =  400;
    if( params['width'] != null ){
      width = params['width'];
    }       
    
    var height = 400;
    if( params['height'] != null ){
      height = params['height'];
    }
                      
    var colorScheme = 'blue';
    if( params['colorScheme'] != null ){
      colorScheme =  params['colorScheme'];
    }              
            

var options = {"colorScheme": "blue", "barOrientation": "vertical", "labelColor": "#000000",  "background": {"hide": true}};
      
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
                                
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderBarChart(doc, records, width, height, options); 
   doc.close(); 
   }
  }
  
}))

graphics.register(new afrous.RenderingUnitAction({
  type : 'PieCategories',
  label : 'Categories',
  description : 'Pie chart - categories',
  iconCls : 'chart-pie-uaction',
  inputs : [
    { name : 'records',
      label : 'input records',
      type : 'Object[]' },
    { name : 'width',
      label : 'width',
      type : 'Integer' },
    { name : 'height',
      label : 'height',
      type : 'Integer' },
    { name : 'colorScheme',
      label : 'color scheme',
      type : 'String',
      options : [ 'blue', 'darkcyan', 'green', 'red', 'grey', 'black' ] }
  ],  
	render : function(params, el) {
	 
  	var records = params['records'];  
    if(!records) return;
    
    var width =  400;
    if( params['width'] != null ){
      width = params['width'];
    }       
    
    var height = 400;
    if( params['height'] != null ){
      height = params['height'];
    }
                      
    var colorScheme = 'blue';
    if( params['colorScheme'] != null ){
      colorScheme =  params['colorScheme'];
    }              
            
                
    var options = {
      	padding: {
		    left: 30, 
		    right: 30, 
		    top: 30, 
		    bottom: 30
	               },	
				
	
	     background: {
		      color: '#ffffff'
	                 },					
	       colorScheme: colorScheme,				
	       axis: {
	            lineWidth:			1.0,
				lineColor:			'#000000',
				tickSize:			7.0,

				labelFont:			'Tahoma',
				labelFontSize:		10,
				labelWidth: 		70.0,
		   		labelColor: '#000000',
    		x: {
		  	ticks: [
				{v:0},
		      	{v:1},
		      	{v:2},
		      	{v:3},
		      	{v:4},
		      	{v:5},
		      	{v:6},
		      	{v:7},
		      	{v:8},
		      	{v:9},
		      	]
      		  },
      		  background: { "hide" : true },
	       }
    }; 
    

       
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
                                
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartTres(doc, records, width, height, options); 
   doc.close(); 
   }
  }
  
}))



function extractoAuthorsOldExtractor(params){
    var autores = [];
    for(i=0;i<params.length;i++){
        if( ( params[i]['dc:author'] != null ) && ( params[i]['dc:author'] != undefined ) && ( params[i]['dc:author'] != "unknown" )){
	    autores.push(params[i]['dc:author']);
		   }
       else {   
       if( ( params[i]['dc:creator'] != null ) && ( params[i]['dc:creator'] != undefined ) && ( params[i]['dc:author'] != "unknown" ) ){
       autores.push(params[i]['dc:creator']);
            }
          }
        }
       if ( autores.length == 0 ){
       alert("No hay autores o hay publicidad en la página");
       return;
								   }   
  		var numeroEfe = 0;
  		var demasAutores = 0;

	 		 for(j=0;j<autores.length;j++){
			 var autorActual = "";     
		     autorActual = autores[j].toLowerCase();       
    			    if ( autorActual.match("efe") != null ){
    				numeroEfe++;
					}
				    else {
					demasAutores++;
					     }
			} 
var dataset = {};

		if( numeroEfe > 0 ){
		dataset['EFE'] = [[0, numeroEfe]];
		}
		if( demasAutores > 0 ){
		dataset['Otros'] = [[0, demasAutores]];
		}
		return dataset;

};


function renderPieChartOldExtractor(doc, records, width, height, options) {
var dataset = extractoAuthorsOldExtractor( records );  			
  doc.write([
'<html>',
'<head>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/prototype/prototype.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/excanvas/excanvas.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr_uncompressed.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr.js">',
'</script>',                                                         
'<script type="text/javascript">',
'function render(dataset, options) {',
'  var pie = new Plotr.PieChart("pie", options);',
'  pie.addDataset( dataset );',
'  pie.render();',
'}',
'window.onload = function(){',
'  render('+afrous.lang.toJSON(dataset)+','+afrous.lang.toJSON(options)+');',
'}',
'</script>',
'</head>',
'<body style="margin:0px;padding:0px;background-color:transparent;">',
'<div id="pieChart"><canvas id="pie" width="'+width+'" height="'+height+'" /></div>',
'</body>',
'</html>'
].join('\n'));
};


function extractAuthorsNewExtractor(params){


var url = 'http://purl.org/dc/elements/1.1/';
var creator = url + 'creator';   
   var autores = [];     
   for(i=0;i<params.length;i++){
   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined )  && ( params[i][creator] != "unknown" )){
   autores.push(params[i][creator]);
              }
        }
   if ( autores.length == 0 ){
   alert("No hay autores o hay publicidad en la página");
   return;
   }
   
	
	
	var numeroEfe = 0;
  	var demasAutores = 0;

	
		
        for(j=0;j<autores.length;j++){
        var autorActual = "";     
        autorActual = autores[j].toLowerCase();
        
        if ( autorActual.match("efe") != null ){
        numeroEfe++;
        }
        else{
        demasAutores++;
        }
   }
var dataset = {};
    if( numeroEfe > 0 ){
    dataset['EFE'] = [[0, numeroEfe]];
    };
    if( demasAutores > 0 ){
    dataset['Otros'] = [[0, demasAutores]];
    };
return dataset;
};

function renderPieChartNewExtractor(doc, records, width, height, options) {

var dataset = extractAuthorsNewExtractor( records );
  doc.write([
'<html>',
'<head>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/prototype/prototype.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/excanvas/excanvas.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr_uncompressed.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr.js">',
'</script>',                                                         
'<script type="text/javascript">',
'function render(dataset, options) {',
'  var pie = new Plotr.PieChart("pie", options);',
'  pie.addDataset( dataset );',
'  pie.render();',
'}',
'window.onload = function(){',
'  render('+afrous.lang.toJSON(dataset)+','+afrous.lang.toJSON(options)+');',
'}',
'</script>',
'</head>',
'<body style="margin:0px;padding:0px;background-color:transparent;">',
'<div id="pieChart"><canvas id="pie" width="'+width+'" height="'+height+'" /></div>',
'</body>',
'</html>'
].join('\n'));
};





function extractAuthorsForComments(params){
var url = 'http://purl.org/dc/elements/1.1/';
var creator = url + 'creator';   
var comment = url + 'comment'; 
var comments = url + 'comments';
   var news = [];
   for(i=0;i<params.length;i++){
	   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined ) && ( params[i][creator] != "unknown" )){
	   news.push(params[i]);
	}
}

  		var commentsEfe = 0;
  		var otherComments = 0;
  		
  		var efeAuthors = 0;
  		var others = 0;
		
	 		 for(j=0;j<news.length;j++){
	 		 	var actualWritter = (news[j][creator]).toLowerCase();
	 		 	
	 		 	
	 				if( actualWritter.match("efe") != null ){
	 				efeAuthors++;
						if( ( ( parseInt(news[j][comments]) ) != NaN ) && ( news[j][comments] != "unknown" ) ){
							commentsEfe = commentsEfe + parseInt(news[j][comments]); 
						}
					}
					else{
					others++;
							if( ( ( parseInt(news[j][comments]) ) != NaN )  && ( news[j][comments] != "unknown" )  ){
						     	otherComments = otherComments + parseInt(news[j][comments]);
								}
				}

		};

var dataset = {};

	if( commentsEfe > 0 ){
	dataset['EFE'] = [[1, commentsEfe]];
	}
 	if( otherComments > 0 ){
	dataset['Otros'] = [[2, otherComments]];
	}

return dataset;

};

function renderBarChart(doc, records, width, height, options) {
var dataset = extractAuthorsForComments(records);
  doc.write([
'<html>',
'<head>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/prototype/prototype.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/lib/excanvas/excanvas.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr_uncompressed.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/plotr/plotr.js">',
'</script>',                                                         
'<script type="text/javascript">',
'function render(dataset, options) {',
'  var bar = new Plotr.BarChart("bar", options);',
'  bar.addDataset(dataset);',
'  bar.render();',
'}',
'window.onload = function(){',
'  render('+afrous.lang.toJSON(dataset)+','+afrous.lang.toJSON(options)+');',
'}',
'</script>',
'</head>',
'<body style="margin:0px;padding:0px;background-color:transparent;">',
'<div><canvas id="bar" width="'+width+'" height="'+height+'" /></div>',
'</body>',
'</html>'
].join('\n'));

};




function extraeAutoresTres(params){

//Cambios para el nuevo extractor
var url = 'http://purl.org/dc/elements/1.1/';
var creator = url + 'creator';   
   var noticiasEfe = [];     
   for(i=0;i<params.length;i++){
   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined )  && ( params[i][creator] != "unknown" ) && ( (params[i][creator].toLowerCase()).match("efe") != null ) ){
   noticiasEfe.push(params[i]);
              }
       }       	graphics.register(new afrous.RenderingUnitAction({
  type : 'PieCategories',
  label : 'Categories',
  description : 'Pie chart - categories',
  iconCls : 'chart-pie-uaction',
  inputs : [
    { name : 'records',
      label : 'input records',
      type : 'Object[]' },
    { name : 'width',
      label : 'width',
      type : 'Integer' },
    { name : 'height',
      label : 'height',
      type : 'Integer' },
    { name : 'colorScheme',
      label : 'color scheme',
      type : 'String',
      options : [ 'blue', 'darkcyan', 'green', 'red', 'grey', 'black' ] }
  ],  
	render : function(params, el) {
	  /*
	  Valores predeterminados de la grafica:
	  width = 500
	  height = 500
	  colorScheme = blue	  
	  */	  
  	var records = params['records'];  
    if(!records) return;
    
    var width =  550;
    if( params['width'] != null ){
      width = params['width'];
    }       
    
    var height = 550;
    if( params['height'] != null ){
      height = params['height'];
    }
                      
    var colorScheme = 'blue';
    if( params['colorScheme'] != null ){
      colorScheme =  params['colorScheme'];
    }              
            
                
	  //Opciones que definen las caracteristicas de la grafica
    var options = {
      	padding: {
		    left: 30, 
		    right: 30, 
		    top: 30, 
		    bottom: 30
	               },	
				
	
	     background: {
		      color: '#ffffff'
	                 },					
	       colorScheme: colorScheme,				
	       axis: {
	            lineWidth:			1.0,
				lineColor:			'#000000',
				tickSize:			7.0,

				labelFont:			'Tahoma',
				labelFontSize:		10,
				labelWidth: 		70.0,
		   		labelColor: '#000000',
    		x: {
    	    	//"hide" : true,
		  	ticks: [
				{v:0},
		      	{v:1},
		      	{v:2},
		      	{v:3},
		      	{v:4},
		      	{v:5},
		      	{v:6},
		      	{v:7},
		      	{v:8},
		      	{v:9},
		      	]
      		  },
      		  background: { "hide" : true },
	       }
    }; 
    

       
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
                                
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartTres(doc, records, width, height, options); 
   doc.close(); 
   }
  }
  
}))






var health = 0;
var sports = 0;
var national = 0;
var tv = 0;
var people = 0;
var science = 0;
var culture = 0;
var international = 0;
var economy = 0;
var travels = 0;

	for(i=0;i<noticiasEfe.length;i++){
		var category = (noticiasEfe[i][url + 'category']).toLowerCase();
			if( category.match("salud") != null ){
				health++;
			};
			if(category.match("deportes") != null ){
				sports++;
			};
			if(category.match("nacional") != null ){
				national++;
			};
			if(category.match("televisi") != null ){
				tv++;
			};
			if(category.match("gente") != null ){
				people++;
			};
			if(category.match("ciencia") != null ){
				science++;
			};
			if(category.match("cultura") != null ){
				culture++;
			};
			if(category.match("internacional") != null ){
				international++;
			};
			if(category.match("econom") != null ){
				economy++;
			};			
			if(category.match("viajes") != null ){
				travels++;
			};
}
		
var dataset = {};

dataset = {
};

	if( health > 0 ){
	dataset['health'] = [[0, health]];
	};
	if( sports > 0 ){	dataset['sports'] = [[0, sports]];
	};
	if( national > 0 ){	dataset['national'] = [[0, national]];
	};
	if( tv > 0 ){	dataset['tv'] = [[0, tv]];
	};
	if( people > 0 ){	dataset['people'] = [[0, people]];
	};
	if( science > 0 ){	dataset['science'] = [[0, science]];
	};
	if( culture > 0 ){	dataset['culture'] = [[0, culture]];
	};
	if( international > 0 ){	dataset['international'] = [[0, international]];
	};
	if( economy > 0 ){	dataset['economy'] = [[0, economy]];
	};
	if( travels > 0 ){	dataset['travels'] = [[0, travels]];
	};
	
return dataset;

};




function renderPieChartTres(doc, records, width, height, options) {

var dataset = extraeAutoresTres( records );
  			
  doc.write([
'<html>',
'<head>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/tarta/lib/prototype/prototype.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/tarta/lib/excanvas/excanvas.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/tarta/plotr_uncompressed.js">',
'</script>',
'<script type="text/javascript" ',
'        src="'+afrous.packages.scriptBaseURL+'/renderers/tarta/plotr.js">',
'</script>',                                                         
'<script type="text/javascript">',
'function render(dataset, options) {',
'  var pie = new Plotr.PieChart("pie", options);',
'  pie.addDataset( dataset );',
'  pie.render();',
'}',
'window.onload = function(){',
'  render('+afrous.lang.toJSON(dataset)+','+afrous.lang.toJSON(options)+');',
'}',
'</script>',
'</head>',
'<body style="margin:0px;padding:0px;background-color:transparent;">',
'<div id="pieChart"><canvas id="pie" width="'+width+'" height="'+height+'" /></div>',
'</body>',
'</html>'
].join('\n'));

};





// if in afrous editor
if (afrous.editor) {
  afrous.packages.loadScript(afrous.packages.scriptBaseURL + '/renderers/plotr-afrous-widget.js');
}

afrous.packages.register(graphics, 'graphics.js');   

})()
