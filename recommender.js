/**
 * Services.recommender Package
 */

// Add:
// p.loadScript(u+'/packages/recommender.js');
// to afrous-stdlib-index.js

(function() {

var recommender = new afrous.UnitActionPackage('Services.Recommender', {
  label : 'recommender',
});


recommender.register(new afrous.UnitAction({
  type : 'Setrate',
  label : 'Set preference',
  description : 'An user rate an object',
  inputs : [
    { name : 'user_id',
      label : 'User',
      type : 'String' },
    { name : 'object_id',
      label : 'Author, Book or Tag',
      type : 'String' },
    { name : 'preference',
      label : 'Calification',
      type : 'Integer' },
    { name : 'type_recom',
      label : 'What´s author, book or tag?',
      type : 'String' }
  ]
  ,
  execute : function(request, callback) {
    if (request.params['user_id']) {
      var url = 'http://localhost:8080/Recommender/mahout?action=setPreference&identifier='+request.params['user_id'];
      if (request.params['type_recom']) url += '&type_recom='+request.params['type_recom'];
      if (request.params['type_recom']=='author') url += '&author='+request.params['object_id'];
      if (request.params['type_recom']=='book') url += '&book='+request.params['object_id'];
      if (request.params['type_recom']=='tag') url += '&tag='+request.params['object_id'];
      if (request.params['preference']) url += '&preference='+request.params['preference'];
      afrous.ajax.jsonp.invoke(url, callback, { globalCallback : true });
    } else {
      callback.onFailure();
    }
  }

}));

recommender.register(new afrous.UnitAction({
  type : 'Recommender',
  label : 'Get recommendations',
  description : 'Returns item recommendations for a given user.',
  inputs : [
    { name : 'user_id',
      label : 'User',
      type : 'String' },
    { name : 'num_recomendations',
      label : 'number of recomendations',
      type : 'Intenger' },
    { name : 'type_recom',
      label : 'type of recomendations',
      type : 'String' }
  ]
  ,
  execute : function(request, callback) {
    if (request.params['user_id']) {
      var url = 'http://localhost:8080/Recommender/mahout?action=getRecommendation&identifier='+request.params['user_id'];
      if (request.params['num_recomendations']) url += '&max_recom='+request.params['num_recomendations'];
      if (request.params['type_recom']) url += '&type_recom='+request.params['type_recom'];
      afrous.ajax.jsonp.invoke(url, callback, { globalCallback : true });
    } else {
      callback.onFailure();
    }
  }

}));

afrous.packages.register(recommender, 'recommender.js');

})();
