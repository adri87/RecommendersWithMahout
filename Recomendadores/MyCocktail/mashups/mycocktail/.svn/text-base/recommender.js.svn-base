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
  type : 'Recommender',
  label : 'Get recommendations',
  description : 'Returns item recommendations for a given user.',
  inputs : [
    { name : 'user_id',
      label : 'User',
      type : 'String' },
    { name : 'num_recomendations',
      label : 'number of recomendations',
      type : 'Intenger' }
  ]
  ,
  execute : function(request, callback) {
    if (request.params['user_id']) {
      var url = 'http://localhost:8080/Recommender/mahout?action=getRecommendation&identifier='+request.params['user_id'];
      if (request.params['num_recomendations']) url += '&max_recom='+request.params['num_recomendations'];
      afrous.ajax.jsonp.invoke(url, callback, { globalCallback : true });
    } else {
      callback.onFailure();
    }
  }

}));

afrous.packages.register(recommender, 'recommender.js');

})();
