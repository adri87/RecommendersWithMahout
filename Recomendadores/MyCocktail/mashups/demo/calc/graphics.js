/*graphics.js 
* It creates different types of diagrams 
*/


NORMAL_MAP = 'Normal';
HYBRID_MAP = 'Hibrid';
SATELITE_TYPE_MAP = 'Satellite';


(function() {
var graphics = new afrous.UnitActionPackage('Renderer.Diagram', {        
  label : 'Diagram'
});

//Pie Chart of authors
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
      	padding: { left: 30, right: 30, top: 30, bottom: 30 },	
	     background: {color: '#ffffff'},					
	       colorScheme: colorScheme,				
	       axis: { lineWidth: 1.0, lineColor:'#000000',tickSize:7.0,labelFont:'Tahoma',labelFontSize:10,labelWidth:70.0,labelColor: '#000000',    
    		x: {"hide": true, 
		  	ticks: [{v:0, label:'Agencia EFE'}, {v:1, label:'Otros autores'}, ]
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
         
   if( el != null){                    
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartNewExtractor(doc, records, width, height, options); 
   doc.close(); 
   }
  }
}
  
}))

//Bar graphics whith the comments
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
            


var options = {"colorScheme": "blue", "axis": {"x": {"hide": true, "tickCount": "5"}, "y": {"hide" : true, "tickCount": "10"}, "tickSize": "4.0", "labelColor": "#000000"}, "background": {"hide": true}, "legend": {"position": {"top": "5px"}}};

      
   var id = new Date().getTime();
   var iframe = afrous.dom.createElement({
   tagName : 'iframe',                           
   width : width,
   height : height,
   frameBorder : 0,
   allowTransparency : true,
   scrolling : 'no'
   }); 
   
   if( el != null ){                              
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderBarChart(doc, records, width, height, options); 
   doc.close(); 
   }
  }
}
  
}))

//Pie chart with the categories
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
      	padding: {left: 30, right: 30,  top: 30,   bottom: 30 },		
	     background: { color: '#ffffff'},					
	       colorScheme: colorScheme,				
	       axis: {lineWidth:1.0,lineColor:'#000000',tickSize:7.0,labelFont:'Tahoma',labelFontSize:10,labelWidth:70.0,labelColor: '#000000',
    		x: {"hide": true, 
		  	ticks: [{v:0},{v:1},{v:2},{v:3},{v:4},{v:5},{v:6},{v:7},{v:8},{v:9},]},
      		  background: { "hide" : true }
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
      
   if( el !=null ){                          
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartTres(doc, records, width, height, options); 
   doc.close(); 
   }
  }
}
  
}))


function extractAuthorsNewExtractor(params){


var url = 'http://purl.org/dc/elements/1.1/';
var creator = url + 'creator';   
   var authors = [];     
   for(i=0;i<params.length;i++){
   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined )  ){
   authors.push(params[i][creator]);
              }
        }
   if ( authors.length == 0 ){
   alert("No hay autores o hay publicidad en la página");
   return;
   }
   
	  var numberEfe = 0;
  	var others = 0;		
        for(j=0;j<authors.length;j++){
        var authorActual = "";     
        authorActual = authors[j].toLowerCase();
          if ( authorActual.match("efe") != null ){
          numberEfe++;
          }
          else{
          others++;
          }
     }
   
var totalNumber = numberEfe + others;
var efePercent = ( numberEfe / totalNumber )*100;
var othersPercent = ( others / totalNumber )*100;

var dataset = {};
    if( numberEfe > 0 ){
    dataset['EFE' + ' ' + '(' + numberEfe + ')' + ' ' + roundNumberTwo(efePercent) + '%'] = [[0, numberEfe]];
    };
    if( others > 0 ){
    dataset['Otros' + ' ' + '(' + others + ')' + ' ' + roundNumberTwo(othersPercent) + '%'] = [[0, others]];
    };    


  if( ( numberEfe == 0 ) && ( others == 0 ) ){
    dataset['Sin autores para esta página'];
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
	   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined ) ){
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
						if( ( ( parseInt(news[j][comment]) ) != NaN ) && ( news[j][comment] != "unknown" ) && ( news[j][comment] != null ) ){
							commentsEfe = commentsEfe + parseInt(news[j][comment]); 

						}
					}
					else{
					others++;
							if( ( ( parseInt(news[j][comment]) ) != NaN )  && ( news[j][comment] != "unknown" ) && ( news[j][comment] != null )   ){
						     	otherComments = otherComments + parseInt(news[j][comment]);
								}
				}

		};

var dataset = {};

var totalComments = commentsEfe + otherComments;
var efeCommentsPercent = ( commentsEfe / totalComments )*100;
var otherCommentsPercent = ( otherComments / totalComments)*100;


		
if( commentsEfe > 0 ){
    dataset['EFE' + ' ' + '(' + commentsEfe + ')' + ' ' +  roundNumberTwo(efeCommentsPercent) + '%'] = [[0, commentsEfe]];
    }
if( otherComments > 0 ){
	dataset['Otros' + ' ' + '(' + otherComments + ')' + ' ' + roundNumberTwo(otherCommentsPercent) + '%'] = [[1, otherComments]];
	};

if( ( commentsEfe == 0 ) && ( otherComments == 0 )  ){
  dataset['Sin comentarios para esta pagina'] = [[2, 1]];
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
'  var pie = new Plotr.BarChart("pie", options);',
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




function extraeAutoresTres(params){

var url = 'http://purl.org/dc/elements/1.1/';
var creator = url + 'creator';   
   var noticiasEfe = [];     
   for(i=0;i<params.length;i++){
   if( ( params[i][creator] != null ) && ( params[i][creator] != undefined )  && ( (params[i][creator].toLowerCase()).match("efe") != null ) ){
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
            
                
    var options = {
      	padding: {
		    left: 30, 
		    right: 30, 
		    top: 30, 
		    bottom: 30
	               },	
				
	
	     background: {color: '#ffffff'},					
	       colorScheme: colorScheme,				
	       axis: { lineWidth:1.0,lineColor:'#000000',tickSize:7.0,labelFont:'Tahoma',labelFontSize:10,labelWidth: 70.0,labelColor: '#000000',
    		x: {
		  	ticks: [{v:0},{v:1},{v:2},{v:3},{v:4},{v:5},{v:6},{v:7},{v:8},{v:9},]
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
     
   if( el != null ){                        
   el.appendChild(iframe);
   if (iframe.contentWindow) {
   var doc = iframe.contentWindow.document;
   doc.open();
   renderPieChartTres(doc, records, width, height, options); 
   doc.close(); 
   }
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
var noEfe = 0;

	for(i=0;i<noticiasEfe.length;i++){
		var category = (noticiasEfe[i][url + 'category']).toLowerCase();
		//alert( category );
			if( category.match("salud") != null ){
				health++;
			};
			if(category.match("deportes") != null ){
				sports++;
			};
			if(category == 'national' != null ){
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
			if(category.match("intern") != null ){
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

var numberCategoriesTotal = health + sports + national + tv + people + science + culture + international + economy + travels;

var healthPercent = ( health / numberCategoriesTotal )*100;
var sportsPercent = ( sports /  numberCategoriesTotal )*100;
var nationalPercent = ( national / numberCategoriesTotal )*100;
var tvPercent = ( tv / numberCategoriesTotal )*100;
var peoplePercent = ( people / numberCategoriesTotal )*100;
var sciencePercent = ( science / numberCategoriesTotal )*100;
var culturePercent = ( culture / numberCategoriesTotal )*100;
var internationalPercent = ( international / numberCategoriesTotal )*100;
var economyPercent = ( economy / numberCategoriesTotal )*100;
var travelsPercent = ( travels / numberCategoriesTotal )*100;


	if( health > 0 ){
	dataset['health' + ' ' + '(' + people + ')' + ' ' + roundNumberTwo(healthPercent)  + '%'] = [[0, health]];
	};
	if( sports > 0 ){
	dataset['sports' + ' ' + '(' + sports + ')' + ' ' + roundNumberTwo(sportsPercent) + '%'] = [[0, sports]];
	};
	if( national > 0 ){
	dataset['national' + ' ' + '(' + national + ')' + ' ' + roundNumberTwo(nationalPercent) + '%'] = [[0, national]];
	};
	if( tv > 0 ){
	dataset['tv' + ' ' + '(' + tv + ')' + ' ' +  roundNumberTwo(tvPercent) + '%'] = [[0, tv]];
	};
	if( people > 0 ){
	dataset['people' + ' ' + '(' + people + ')' + ' ' + roundNumberTwo(peoplePercent) + '%'] = [[0, people]];
	};
	if( science > 0 ){
	dataset['science' + ' ' + '(' + science + ')' + ' ' + roundNumberTwo(sciencePercent) + '%'] = [[0, science]];
	};
	if( culture > 0 ){
	dataset['culture' + ' ' + '(' + culture + ')' + ' ' + roundNumberTwo(culturePercent) + '%'] = [[0, culture]];
	};
	if( international > 0 ){
	dataset['international' + ' ' + '(' + international + ')' + ' ' + roundNumberTwo(internationalPercent) + '%'] = [[0, international]];
	};
	if( economy > 0 ){
	dataset['economy' + ' ' + '(' + economy + ')' + ' ' + roundNumberTwo(economyPercent) + '%'] = [[0, economy]];
	};
	if( travels > 0 ){
	dataset['travels' + ' ' + '(' + travels + ')' + ' ' + roundNumberTwo(travelsPercent) + '%'] = [[0, travels]];
	};
	
		
	if( health == 0 && sports == 0 && national == 0 && tv == 0 && people == 0 && science == 0 && culture == 0 && international == 0 && 
	economy == 0 && travels == 0 ){
	noEfe = 1;
	dataset['Sin noticias de EFE'] = [[0, noEfe ]];
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

function roundNumberTwo( number ){
  var original = parseFloat( number );
  result = Math.round(original*100)/100;
  return result;
}	

// if in afrous editor
if (afrous.editor) {
  afrous.packages.loadScript(afrous.packages.scriptBaseURL + '/renderers/plotr-afrous-widget.js');
}

afrous.packages.register(graphics, 'graphics.js');   

})()


NORMAL_MAP = 'Normal';
HYBRID_MAP = 'Hibrid';
SATELITE_TYPE_MAP = 'Satellite';

(function() {

var google = new afrous.UnitActionPackage('Renderer.GoogleMaps', {
  label : 'Google Maps EFE'
});


google.register(new afrous.RenderingUnitAction({

  type : 'GoogleMapsMarker',
  label : 'Google Maps Marker for EFE',
  description : 'Positionate locations with labels in a map with Google Maps',
  iconCls : 'gmaps-uaction',
  inputs : [
    { name : 'records',
      label : 'Input Records',
      type : 'Object[]' },
    { name : 'labelField',
      label : 'Label Field',
      type : 'String',
      options : [] },
    { name : 'valueField',
      label : 'Address Field',
      type : 'String',
      options : [] },
    { name : 'width',
      label : 'Width',
      type : 'Integer' },
    { name : 'height',
      label : 'Height',
      type : 'Integer' },
    { name : 'zoomController',
      label : 'Zoom Controller',
      type : 'String',
      formElement : "CHECK_BOX" },
    { name : 'mapTypeController',
      label : 'Map Type Controller',
      type : 'String',
      formElement : "CHECK_BOX" },
    { name : 'mapType',
      label : 'Map type',
      type : 'String', 
      options : [NORMAL_MAP, HYBRID_MAP, SATELITE_TYPE_MAP] },
    { name : 'apiKey',
      label : 'API Key',
      type : 'String' }
  ],

  render : function(params, el) {

    var records = params['records'];
    if (!records) 
      return;
    var labelField = params['labelField'];
    var valueField = params['valueField'];
    
    var width  = params['width']  || 500;
    var height = params['height'] || 200;
    
   //var apiKey = params['apiKey'] || 'ABQIAAAAQLVYVvhTjXRPtKDa9AsPlhQs1SMlRZa-qyzdhC9kofgBeB67sRRcX48ZCyilXhFK6qqej_uY3YV6AA';
      // New Api key 
      var apiKey = params['apiKey'] ||  'ABQIAAAARbIeqgfLyEpf4mZMvY4LDBRzAr0GC76kc9FrIeWVoeArWDTgBxTfcjdZONmrfVKmx95Zuuu_VQW8VQ';
    
    var mapType = params['mapType'] || NORMAL_MAP;
    
    var mapTypeController = false;
    mapTypeController = params['mapTypeController'] || false;
    if(mapTypeController == 'true')
      mapTypeController = true;
  
    var zoomController = false;
    zoomController = params['zoomController'] || false;
    if(zoomController == 'true')
      zoomController = true;

    var iframe = afrous.dom.createElement({
      tagName : 'iframe',
      width : width,
      height : height,
      frameBorder : 0,
      allowTransparency : true,
      scrolling : 'no'
    });
    
    if( el != null ){
    el.appendChild(iframe);
    if (iframe.contentWindow) {
      var doc = iframe.contentWindow.document;
      doc.open();
      renderMapWithMarkers(doc, records, width, height, labelField, valueField, mapTypeController, zoomController, mapType, apiKey); 
      doc.close(); 
    }
  }
}

}))

function renderMapWithMarkers(doc, records, width, height, labelField, valueField, mapTypeController, zoomController, mapType, apiKey) {

    var dataset = [];
    afrous.lang.forEach(records, function(r, i) {
    var label =  quotesSearch( ( r[labelField] ).toString() || i );
   	var location =  quotesSearch( afrous.lang.cast('String', r[valueField] || r) );
    var titleSeparated =  (label.toString()).split(',',2);

    var author = records[i]['http://purl.org/dc/elements/1.1/creator'];

    for(i=0;i<titleSeparated.length;i++){
        var shortestTitle = titleSeparated[0];
        if( ( ( titleSeparated[i] ).length < shortestTitle.length ) && ( titleSeparated[i].length > 2 ) ){
            shortestTitle = titleSeparated[i];
          }
     }

    label = shortestTitle;

    	if( ( location != "unknown" ) && (label != "unknown" ) && ( location != "" ) && ( label != "" ) &&	 ( location != null ) && ( label != null ) && ( location.match("object")  == null ) ){
   	  	dataset.push({location: location, label: label, author: author});
   	  }

    })
  
  function drawAllPoints(){
    var string = '';
    var points = [];
    

    
    for(var i=0; i<dataset.length; i++)
    {
      var rep = false;
      for(var j=i+1; j<dataset.length; j++)
      {
        if(dataset[i]['location'] == dataset[j]['location'] && dataset[i]['label'] != dataset[j]['label'])
        {
          dataset[j]['label'] = dataset[i]['label'] + '<br>' + dataset[j]['label'];
          rep = true;
          break;
        }
      }
      if(!rep)
        points.push(dataset[i]);
    }
    dataset = points;
    
    var newCreator;
    
    dataset.forEach(function(point){
  	newCreator = (point['author']).toLowerCase();
//    alert(newCreator);
			if( newCreator.match("efe") != null ){
				string = string+'findLocationAndAddMarker(true, markers,"'+point['location'].replace(/(\\|\")/g, '\\$1')+'", "'+point['label']+'");';
			}
			else{
       string = string+'findLocationAndAddMarker(false, markers,"'+point['location'].replace(/(\\|\")/g, '\\$1')+'", "'+point['label']+'");';
       };
      
    });
    return string;
  }

  doc.write([
'<html>',
'  <head>',
'    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>',
'    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key='+apiKey+'" type="text/javascript"></script>',
'    <script type="text/javascript">',
'    var map;',
'    function initialize() {',
'      if (GBrowserIsCompatible()) {',
'        map = new GMap2(document.getElementById("map_canvas"));',
setMapType(mapType),
addZoomMapController(zoomController),
addTypeMapController(mapTypeController),
'        map.setCenter(new GLatLng(0,0));',
'        geocoder = new GClientGeocoder();',
'        var markers = [];',
drawAllPoints(),
'      }',
'    }',
'    function findLocationAndAddMarker(authorEfe, markers, address, label)',
'    {',
'      if (geocoder) {',
'        geocoder.getLatLng(',
'          address,',
'          function(point) {',
'						if(point  != null){',
'            addMarker(authorEfe, point, label);',
'            markers.push(point);',
'            setCenter(map,markers);',
'          }',
'					}',
'        );',
'      }',  
'    }',
'    function addMarker(authorEfe, point, label)',
'    {',
'			var efeIcon = new GIcon(G_DEFAULT_ICON);',
'			efeIcon.image ="' + afrous.packages.scriptBaseURL + '/renderers/img/google-maps-marker.png";',
'     if( efeIcon.image == null || efeIcon.image == undefined ){',
'       efeIcon.image = "img/google-maps-marker.png";',
'     };',
'			var markerOptionEfe = { icon:efeIcon };',
'			if( authorEfe == true ){',
'				var marker = new GMarker( point, markerOptionEfe );',
'			}',
'		  else{',
'     var marker = new GMarker(point);',
'			}',
'      GEvent.addListener(marker, "click", function() {',
'        marker.openInfoWindowHtml(label);',
'      });',
'      map.addOverlay(marker);',
'    }',
'    function setCenter(map,pointArray){',
'      var north =  -90;',
'      var south =   90;',
'      var east  = -180;',
'      var west  =  180;',
'      pointArray.forEach(function(point){',
'       if( point.lat != null) {',
'        if(north < point.lat())',
'           north = point.lat();',
'        if(south > point.lat())',
'           south = point.lat();',
'        if(east  < point.lng())',
'           east  = point.lng();',
'        if(west  > point.lng())',
'           west  = point.lng();',
'   };',
'      });',
'      var latitudeAvg  = Math.abs((north-south)/2);',
'      var longitudeAvg = Math.abs((west-east)/2);',
'      var bounds = new GLatLngBounds(new GLatLng(south,west), new GLatLng(north,east));',
'      var zoom = map.getBoundsZoomLevel(bounds);',
'      map.setCenter(new GLatLng(south+latitudeAvg,west+longitudeAvg), zoom);',
'    }',
'    </script>',
'  </head>',
'  <body onload="initialize()" onunload="GUnload()">',
'    <div id="map_canvas" style="width: '+width+'px; height: '+height+'px"></div>',
'  </body>',
'</html>'
].join('\n'));

}

function setMapType(mapType)
{
  function auxSetMapType(type)
  {
    return 'map.setMapType('+type+')';
  }

  if(mapType == SATELITE_TYPE_MAP)
    return auxSetMapType('G_SATELLITE_MAP');
  if(mapType == HYBRID_MAP)
    return auxSetMapType('G_HYBRID_MAP');
  return auxSetMapType('G_NORMAL_MAP');
}

function addZoomMapController(controller)
{
  if(controller)
    return 'map.addControl(new GLargeMapControl());'
  return '';
}

function addTypeMapController(controller)
{
  if(controller)
    return 'map.addControl(new GMapTypeControl());'
  return '';
}

// if in afrous editor
if (afrous.editor) {
  afrous.packages.loadScript(afrous.packages.scriptBaseURL + '/renderers/plotr-afrous-widget.js');
}

function quotesSearch( oldString ){
		 var oldStringToString = oldString.toString()
 		 var b = /"/g
		 var newString = oldStringToString.replace(b,"'")
		 return newString
};

afrous.packages.register(google, 'graphics.js');

})()


