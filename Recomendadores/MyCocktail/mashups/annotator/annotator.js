/* 
 selectores.js
*/
var arrayElements = new Array();
var arrayNames = new Array();


$(document).ready(function(){
//link CSS
  $("head").append("<link rel='stylesheet' href='http://dl.dropbox.com/u/6030239/jquery-ui-1.8.6.custom.css' rel='stylesheet' type='text/css'>");

//creates a div in which the popup window is loaded.
  $("body").append("<div id='dialog' title='Extractor&#39;s Generator'><div id='botones' class='button'>" +
                   "<div type='text' value='texto dentro del control'id'texto' />" + 
                   "</div><ul id='listaContenido'></ul></div></div></div>");

//creates a button wich open the popup window when it is closed.
  $("body").append("<button id='opener'></button>");

//disable links
   $('a[href],:input,:image,:button,span,a').bind('click', function(e){
      return false;
   });

    $("body :nth-child(n)").bind('mouseover', function(e){
	e.stopPropagation();
         	($(this).not("#dialog :nth-child(n),.ui-dialog :nth-child(n),#opener :nth-child(n)")).css({border: "2px solid #6DA6D1"});
                }).mouseout(function(){
			$(this).css("border", "none");
	});

    startExtractor();

});

//Add the text to the generator
function startExtractor(){
  var extractorName = prompt('Enter the name of the extractor','Extractor'+ '\''+'s name');
  if(extractorName != "" && extractorName != null){  
  popup();
  var extractorBeginning = "dc:&nbsp;http://purl.org/dc/elements/1.1/"+'<br>'+
                          "owl:&nbsp;http://www.w3.org/2002/07/owl#"+'<br>'+
                          "rdf:&nbsp;http://www.w3.org/1999/02/22-rdf-syntax-ns#"+'<br>'+
                          "sioc:&nbsp;http://rdfs.org/sioc/ns#"+'<br>'+
                          "sc:&nbsp;http://lab.gsi.dit.upm.es/scraping.rdf#"+'<br>'+
                          "loc:&nbsp;http://www.daml.org/experiment/ontology/location-ont#"+'<br>'+'<br>'+      
                          "_:"+ extractorName +"noticia"+ ":"+'<br>'+
                          "&nbsp;&nbsp;rdf:type:&nbsp;sc:Fragment"+'<br>'+			  
			  "&nbsp;&nbsp;sc:selector:"+'<br>'+
			  "&nbsp;&nbsp;&nbsp;&nbsp;*:"+'<br>'+
			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:type:&nbsp;sc:UriPatternSelector"+'<br>'+
			  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:value:&nbsp;"+'\"'+"http://"+ document.domain +"/*"+ '\"' +'<br>'+
		  	  "&nbsp;&nbsp;sc:identifier:"+'<br>'+
                          "&nbsp;&nbsp;&nbsp;&nbsp;*:"+'<br>'+
                          "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:type:&nbsp;sc:BaseUriSelector"+'<br>'+
                          "&nbsp;&nbsp;sc:subfragment:";

	arrayElements[0] = extractorBeginning;
	if(document.getElementById("listaContenido").innerHTML == ""){
		document.getElementById("listaContenido").innerHTML = arrayElements[0];
	}
  }else{
    alert("You must enter a valid name");
    emptyList();
  }
};


//load the popup if the extractor's name has been gotten
function popup(){
$("#dialog").css("display", "block");
$("#dialog").css("text-align", "left");
$("#dialog").css("font-size", "11pt");
$("#dialog").css("color", "blue");

	$( "#dialog" ).dialog({ 
		autoOpen: false,
		show: "slide",
		//hide: "fadeOut",		
		closeOnEscape: true,
		bgiframe: true,
		modal: true,
		stack: true,
		height: 600,		
		width:$(document).width()*0.65,
		minHeight:600,
		minWidth:$(document).width()*0.65,
		//maxWidth:$(document).width()-500,
		buttons: {
			"Empty List": function() {
				emptyList();                                  
			},
			"Erase Last Element": function() {
				
				eraseLastElement();
			},
			"Erase An Element": function() {
				eraseSelectedElement();
			},
			"get URL": function() {
				alert(window.location);
			}
		}				
	 });  
	$( "#opener" ).click(function() {
		$( "#dialog" ).dialog( "open" );
		return false;
	});
}


$(document).ready(function(){
 ($("body :nth-child(n) *:not(.ui-dialog)").not(".ui-dialog :nth-child(n)")).bind('click', function(e){
      //Avoid event's propagation 
      e.stopPropagation();    
     var clickedElement = ($(e.target).closest(this.tagName).get(0).tagName);
       
if( clickedElement.toLowerCase() != "button" ){
     	var numParents = $(this).parents().length;
     	var currentNode = $(this);
	var attrString = elementPath(clickedElement,numParents,currentNode);

//gets the extractor's name
        var selectorName = prompt('Enter the name of the selector you want to extract',''); 
	selectorName = selectorName.replace(/ /g, '').replace(/[ ]*$/, '');    
        arrayNames[arrayNames.length] = selectorName;
           
//generates the extractor of each selector
    if(selectorName != ""){
//in html
   attrString = attrString.toLowerCase();
   attrString = attrString.replace("#cambiafondo","").replace("cambiafondo","");   
   
if(attrString[attrString.length-2] == " "){
   attrString = attrString.slice(0,-1);
  }
if(attrString[attrString.length-1] == " "){
   attrString = attrString.slice(0,-1);
  }
attrString = attrString.replace(". ","").replace(" . "," ").replace(" p ."," p.").replace(" span ."," span.").replace(". ","");


   var extractorSelector = ('<br>'+"&nbsp;&nbsp;&nbsp;&nbsp;*:"+ '<br>'+ 					'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:Literal' + '<br>' 				     +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:relation:&nbsp;dc:" + selectorName + '<br>' +
			     "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:selector:" + '<br>' + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*:" + '<br>' 				     +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:type:&nbsp;&nbsp;sc:CssSelector" + '<br>' + 
			     "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:value:&nbsp;&quot;" + attrString+'&quot;');


	if(selectorName == "location"){
		var extractorSelector = ('<br>'+"&nbsp;&nbsp;&nbsp;&nbsp;*:"+ '<br>'+ 				'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;loc:Location' + '<br>' 				     +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:relation:&nbsp;loc:location"+ '<br>' +
			     "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:selector:" + '<br>' + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*:" + '<br>' 				     +"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:type:&nbsp;&nbsp;sc:CssSelector" + '<br>' + 
			     "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:value:&nbsp;&quot;" + attrString+'&quot;'+ '<br>' + 				"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:subfragment:"+ '<br>' +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*:" + '<br>'+
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:type:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:Literal" + '<br>' +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:relation:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:label" + '<br>' +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;sc:selector:" + '<br>' +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;*:" + '<br>' +
			"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;rdf:type:&nbsp;&nbsp;sc:RootSelector");
}

//generates the text file of the extractor       	
   	var arrayLength = arrayElements.length;
        arrayElements[arrayLength] = extractorSelector;
	document.getElementById("listaContenido").innerHTML = document.getElementById("listaContenido").innerHTML + arrayElements[arrayLength];
      
        if(selectorName == null && $("#dialog li").size() > 1){
           eraseLastElement();	   
        }
      }else{
        alert("You must enter a valid name");
      }     
      };     
   });
});




/****Auxiliar fucntions****/
 
//Erase the last element of the list
function eraseLastElement(){
        if(arrayElements.length > 1){	  
	   arrayElements.pop();
	   arrayNames.pop();	
	   $('#listaContenido').empty();
           for(i = 0; i<arrayElements.length; i++){
	         document.getElementById("listaContenido").innerHTML = document.getElementById("listaContenido").innerHTML + arrayElements[i];
      	   }
	};
};

//Erase the selected element
function eraseSelectedElement(){	
        if(arrayElements.length > 1){
	   var nameExtraction = prompt('Enter the name of the extracted element you want to remove','');	   
	   var number = findElementPosition(nameExtraction) + 1;		
	   if(number > 0 && !(isNaN(number)) && number < arrayElements.length && number != -1){	   
		$('#listaContenido').empty();		
		arrayElements.splice(number,1);
		arrayNames.splice(number-1,1);     
		for(i = 0; i<arrayElements.length; i++){
	         	document.getElementById("listaContenido").innerHTML = document.getElementById("listaContenido").innerHTML + this.arrayElements[i];
      	        }
	   }else if(number = -1){
		alert("Name not found");	
           }else{
		alert("The name entered is not valid.");
           }
	}else{
		alert("No extracted items to remove");
	}
};

//Empty the list
function emptyList(){
	$('#listaContenido').empty();
        arrayElements = new Array();
        arrayNames = new Array();
        startExtractor();	        
};  


//returns the position's number of an element
function findElementPosition(elementName){
   if(arrayNames.length > 0){
      for(i = 0; i < arrayNames.length; i++){	  
          if( arrayNames[i] == elementName){             
              return i;
          }
      }
   }else{	
      return -1;
   }
};

//Auxiliar fuction for trimming the string
function attrTrimSplitter(attrSubString){
	attrSubString = attrSubString.trim();
	subC = new Array();
	subC = (attrSubString).split(" ");
//alert(subC);
	if(subC[1] != undefined){
		return "";
	}else{
		return subC[0];
	}
}

//Creates a string which contains the path of he clicked element
function elementPath(clickedElement,numParents,currentNode){
var attrString = "";
     for(i=0;i<=numParents;i++){
       	     
     	//if (currentNode.attr("id") != ""){
        //	 attrString = "#" + attrTrimSplitter(currentNode.attr("id")) + " " + attrString;
    	//}
    	if (currentNode.attr("class") != ""){
         	attrString = "." + attrTrimSplitter(currentNode.attr("class")) + " " + attrString;
     	}
	if((currentNode.get(0).tagName == "P") || (currentNode.get(0).tagName == "H1") || 
	(currentNode.get(0).tagName == "H2")|| (currentNode.get(0).tagName == "H3")|| (currentNode.get(0).tagName == "EM") ||
	(currentNode.get(0).tagName == "SPAN")){
 		attrString = attrTrimSplitter(currentNode.get(0).tagName) + " " + attrString;       
		attrString.stopPropagation;               
	}
        currentNode = currentNode.parent();
     }	
	return attrString;
}

