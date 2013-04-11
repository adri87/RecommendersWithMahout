package es.upm.dit.gsi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.JDBCDataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.cf.taste.common.TasteException;

import es.upm.dit.gsi.logger.Logger;
import es.upm.dit.gsi.jdbc.Authors;
import es.upm.dit.gsi.jdbc.Books;
import es.upm.dit.gsi.jdbc.Tags;
import es.upm.dit.gsi.jdbc.Users;
import es.upm.dit.gsi.connection.Configuration;

public class Mahout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JDBCDataModel dataModel;
	private UserNeighborhood neighborhood;
	private UserSimilarity similarity;
	private Recommender recommender;
	public static Configuration conf;

	private static final Logger LOGGER = Logger.getLogger("servlet.Mahout");

	/**
	 * init del servlet
	 * 
	 * @throws ServletException si se produce un error
	 */
	public void init() throws ServletException {
		super.init();
		conf = Configuration.getConfiguration();
	}
	
	public void init(String type_recom) throws Exception {
		if (type_recom.equals("author"))
			dataModel = new MySQLJDBCDataModel(conf.getDataSource(), "preference_table_author", "user_id","author_id", "preference");
		else if (type_recom.equals("book"))
			dataModel = new MySQLJDBCDataModel(conf.getDataSource(), "preference_table_book", "user_id","book_id", "preference");
		else if (type_recom.equals("tag"))
			dataModel = new MySQLJDBCDataModel(conf.getDataSource(), "preference_table_tag", "user_id","tag_id", "preference");
		LOGGER.info("dato model es"+dataModel);
		try {
			similarity = new PearsonCorrelationSimilarity (dataModel);
		} catch (TasteException e){
			e.printStackTrace();
		}
		neighborhood = new NearestNUserNeighborhood(2, similarity, dataModel);
		recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
	}

   /**
	 * Pone en la base de datos la preferencia de un cierto usuario asociada a un objeto.
	 * 
	 * @param res, HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
	public synchronized void setPreference(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException{
		try {
			String gesforId = req.getParameter(Constants.IDENTIFIER);
			long userId = Users.userIdentifier(gesforId);
			String type_recom = req.getParameter(Constants.TYPE_RECOM);
			init(type_recom);
			if (type_recom.equals("author")){
				String gesforAuthorId = req.getParameter(Constants.AUTHOR);
				long authorId = Authors.authorIdentifier(gesforAuthorId);
				float preference = new Float(req.getParameter(Constants.PREFERENCE_PARAM));
				dataModel.setPreference(userId, authorId, preference);
				LOGGER.info("El usuario " + gesforId + " añade un valoración de " + preference + " al autor " + gesforAuthorId);
			}else if (type_recom.equals("book")){
				String gesforBookId = req.getParameter(Constants.BOOK);
				long bookId = Books.bookIdentifier(gesforBookId);
				float preference = new Float(req.getParameter(Constants.PREFERENCE_PARAM));
				dataModel.setPreference(userId, bookId, preference);
				LOGGER.info("El usuario " + gesforId + " añade un valoración de " + preference + " al libro " + gesforBookId);
			}else if (type_recom.equals("tag")){
				String gesforTagId = req.getParameter(Constants.TAG);
				long tagId = Tags.tagIdentifier(gesforTagId);
				float preference = new Float(req.getParameter(Constants.PREFERENCE_PARAM));
				dataModel.setPreference(userId, tagId, preference);
				LOGGER.info("El usuario " + gesforId + " añade un valoración de " + preference + " a la etiqueta " + gesforTagId);
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}

	}

   /**
	 * Genera la recomendación para un determinado usuario del servicio.
	 * 
	 * @param req,HttpServletRequest
	 * @param res,HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
	private void getRecommendation(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		try {
			String type_recom = req.getParameter(Constants.TYPE_RECOM);
			init(type_recom);
			PrintWriter out =res.getWriter();
			String callback = req.getParameter(Constants.CALLBACK);
			String identifier = req.getParameter(Constants.IDENTIFIER);
			Long userId = Users.getUserId(identifier);
			Integer max = new Integer(req.getParameter(Constants.MAX_RECOM_PARAM));
			if (userId !=null){
				List<RecommendedItem> recommendations = recommender.recommend(userId, max);
				out.print(callback+ "(['");
				for (int i = 0; i < recommendations.size(); i++) {
					RecommendedItem recommendation = recommendations.get(i);
					long itemId = recommendation.getItemID();
					String object ="";
					if (type_recom.equals("author"))
						object = Authors.getGesforAuthorId(itemId);
					else if (type_recom.equals("book"))
						object = Books.getGesforBookId(itemId);
					else if (type_recom.equals("tag"))
						object = Tags.getGesforTagId(itemId);
					out.print(object);
					if (i + 1 != recommendations.size())
						out.print("','");
				}
				out.print("'])");
			} else {
				LOGGER.warning("No se puede dar recomendación ya que no existe el usuario"); 
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Nos devuelve los objetos que ha valorado un usuario dado.
	 * 
	 * @param req,HttpServletRequest
	 * @param res,HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
	private void itemsFromUser(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		try{
			String type_recom = req.getParameter(Constants.TYPE_RECOM);
			init(type_recom);
			String gesforId= req.getParameter (Constants.IDENTIFIER);
			long userId = Users.getUserId(gesforId);
			if (userId!=-1){
				LOGGER.info("Los objetos valorados por el usuario" + gesforId + "son:");
				dataModel.getItemIDsFromUser(userId);
			} else {
				LOGGER.warning("Usuario no encontrado");
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

   /**
	 * Nos devuelve la tabla de preferencias guardada en la base de datos
	 * 
	 * @param res,HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
	private void table(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		try{
			String type_recom = req.getParameter(Constants.TYPE_RECOM);
			init(type_recom);
			dataModel.getUserIDs();
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		}
	}

	/**
	 * Elimina un  elemento de la base de datos.
	 * 
	 * @param req,HttpServletRequest
	 * @param res,HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
	private void removePreference(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		try {
			String gesforId = req.getParameter(Constants.IDENTIFIER);
			long userId = Users.getUserId(gesforId);
			String type_recom = req.getParameter(Constants.TYPE_RECOM);
			init(type_recom);
			if (type_recom.equals("author")){
				String gesforAuthorId = req.getParameter(Constants.AUTHOR);
				long authorId = Authors.authorIdentifier(gesforAuthorId);
				if (userId!=-1){
					dataModel.removePreference(userId, authorId);
					LOGGER.info("Eliminamos la valoración del usuario " + gesforId + " para el autor " + gesforAuthorId);
				} else {}
			}else if (type_recom.equals("book")){
				String gesforBookId = req.getParameter(Constants.BOOK);
				long bookId = Books.bookIdentifier(gesforBookId);
				if (userId!=-1){
					dataModel.removePreference(userId, bookId);
					LOGGER.info("Eliminamos la valoración del usuario " + gesforId + " para el objeto " + gesforBookId);
				} else {}
			}else if (type_recom.equals("tag")){
				String gesforTagId = req.getParameter(Constants.TAG);
				long tagId = Tags.tagIdentifier(gesforTagId);
				if (userId!=-1){
					dataModel.removePreference(userId, tagId);
					LOGGER.info("Eliminamos la valoración del usuario " + gesforId + " para la etiqueta " + gesforTagId);
				} else {}
			}
		} catch (Exception e) {
			e.printStackTrace();
			res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
	}

   /**
	 * Ejecuta la acción solicitada por el cliente.
	 * 
	 * @param req,HttpServletRequest
	 * @param res,HttpServletResponse
	 * @throws ServletException si se produce algun error
	 * @throws IOException si se produce algun error
	 */
   protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println("Esperando respuesta");

		if (req.getParameter("action").equals("getRecommendation")) {
			getRecommendation(req, res);
		} else if (req.getParameter("action").equals("setPreference")) {
			setPreference(req, res);
		} else if (req.getParameter("action").equals("table")) {
			table(req, res);
		} else if (req.getParameter("action").equals("itemsUSer")) {
			itemsFromUser(req, res);
		} else if (req.getParameter("action").equals("removePreferece")) {
			removePreference(req,res);
   		}
		res.setStatus(HttpServletResponse.SC_OK);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       // TODO Auto-generated method stub
   }

}