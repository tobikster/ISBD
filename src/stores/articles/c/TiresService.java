package stores.articles.c;

import core.c.DatabaseManager;
import core.c.EntityValidator;
import core.m.DatabaseException;
import core.m.ResultRow;
import finance.m.VATRate;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import stores.articles.c.validators.tires.DOTValidator;
import stores.articles.c.validators.tires.TireSizeValidator;
import stores.articles.c.validators.tires.TireValidator;
import stores.articles.c.validators.tires.TreadValidator;
import stores.articles.m.*;
import stores.groups.c.GroupsService;
import stores.groups.m.ArticlesGroup;
import stores.producers.c.ProducersService;
import stores.producers.m.Producer;
import utils.m.WorkingMap;

public class TiresService {
	// <editor-fold defaultstate="collapsed" desc="Creating object">
	// <editor-fold defaultstate="collapsed" desc="Singleton">
	public static TiresService getInstance() {
		return InstanceHolder.p_instance;
	}

	private static final class InstanceHolder {
		private static final TiresService p_instance = new TiresService();
	}
	// </editor-fold>

	private TiresService() {
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="DOT methods">
	public DOT getDOT(int DOTId) throws SQLException {
		String sQuery = "SELECT * FROM DOTy WHERE IdDOTu=" + DOTId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("DOT with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		DOT dot = new DOT();
		dot.setId(DOTId);
		dot.setDot(result.getString(2));

		return dot;
	}

	public DOT getDOT(String DOTValue) throws SQLException {
		String sQuery = "SELECT * FROM DOTy WHERE DOT='" + DOTValue + "';";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("DOT with given value does not exist!");
		}
		ResultRow result = results.get(0);

		DOT dot = new DOT();
		dot.setId(result.getInt(1));
		dot.setDot(DOTValue);

		return dot;
	}

	public List<DOT> getDOTs() throws SQLException {
		String sQuery = "SELECT * FROM DOTyOpon;";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<DOT> dots = new ArrayList<>();

		for (ResultRow result : results) {
			DOT dot = new DOT();
			dot.setId(result.getInt(1));
			dot.setDot(result.getString(2));
			dots.add(dot);
		}

		return dots;
	}

	public void addDOT(DOT dot) throws SQLException, DatabaseException {
		EntityValidator<DOT> validator = new DOTValidator();
		validator.validate(dot);

		String sQuery = "INSERT INTO DOTy(DOT) VALUES ('" + dot.getDot() + "');";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="TIRE methods">
	public Tire getTire(int tireId) throws SQLException {
		String sQuery = "SELECT * FROM Opony WHERE IdOpony=" + tireId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tire with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Tire tire = new Tire();
		tire.setId(tireId);
		tire.setGroup(GroupsService.getInstance().getArticleGroup(result.getInt(2)));
		tire.setTread(getTread(result.getInt(3)));
		tire.setSize(getTireSize(result.getInt(4)));
		tire.setLoadIndex(LoadIndex.valueOf(result.getInt(5)));
		tire.setSpeedIndex(SpeedIndex.valueOf(result.getString(6)));
		tire.setMargin(result.getDouble(7));
		tire.setGrossPrice(result.getDouble(8));

		sQuery = "SELECT DOTy.IdDOTu, DOTy.DOT, Liczba FROM DOTyOpon "
				+ "INNER JOIN DOTy ON DOTyOpon.IdDOTu=DOTy.IdDOTu WHERE IdOpony=" + tireId + ";";
		results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (!results.isEmpty()) {
			Map<DOT, Integer> tiresDOTs = new WorkingMap<>();
			DOT currentDOT;
			for (ResultRow rr : results) {
				currentDOT = new DOT(rr.getInt(1), rr.getString(2));
				tiresDOTs.put(currentDOT, rr.getInt(3));
			}
			tire.setTireDOTs(tiresDOTs);
		}

		return tire;
	}

	public List<Tire> getTires(boolean fetchAll, ArticlesGroup group) throws SQLException {
		String sGroupsCondition = "";
		if (group != null && group.getCode() > 0) {
			sGroupsCondition += " AND Opony.KodGrupy=" + group.getCode();
		}
		String sQuery = "SELECT Opony.IdOpony, IndeksNosnosci, IndeksPredkosci, Marza, CenaBrutto, "
				+ "BieznikiOpon.IdBieznika, BieznikiOpon.Nazwa, Producenci.IdProducenta, Producenci.Nazwa, "
				+ "RozmiaryOpon.IdRozmiaru, RozmiaryOpon.Szerokosc, RozmiaryOpon.Profil, RozmiaryOpon.Srednica, "
				+ "GrupyTowarowe.KodGrupy, GrupyTowarowe.Nazwa, StawkiVAT.IdStawki, StawkiVAT.Stawka "
				+ "FROM ((Opony INNER JOIN "
				+ "(BieznikiOpon INNER JOIN Producenci ON BieznikiOpon.IdProducenta=Producenci.IdProducenta) "
				+ "ON Opony.IdBieznika=BieznikiOpon.IdBieznika) "
				+ "INNER JOIN RozmiaryOpon ON Opony.IdRozmiaru=RozmiaryOpon.IdRozmiaru) "
				+ "INNER JOIN "
				+ "(GrupyTowarowe INNER JOIN StawkiVAT ON GrupyTowarowe.VAT=StawkiVAT.IdStawki) "
				+ "ON Opony.KodGrupy=GrupyTowarowe.KodGrupy "
				+ "WHERE Typ='o'" + sGroupsCondition + ";";

		if (fetchAll) {
			DatabaseManager.getInstance().maintainConnection();
		}
		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		int iCounter = 0;

		List<Tire> tires = new ArrayList<>();
		for (ResultRow result : results) {
			iCounter++;
			Tire tire = new Tire();
			//Basic tire info
			tire.setId(result.getInt(1));
			tire.setLoadIndex(LoadIndex.valueOf(result.getInt(2)));
			tire.setSpeedIndex(SpeedIndex.valueOf(result.getString(3)));
			tire.setMargin(result.getDouble(4));
			tire.setGrossPrice(result.getDouble(5));
			//Tire tread info
			Tread tread = new Tread();
			tread.setId(result.getInt(6));
			tread.setName(result.getString(7));
			Producer producer = new Producer();
			producer.setId(result.getInt(8));
			producer.setName(result.getString(9));
			tread.setProducer(producer);
			tire.setTread(tread);
			//Tire size info
			TireSize size = new TireSize();
			size.setId(result.getInt(10));
			size.setWidth(result.getString(11));
			size.setProfile(result.getString(12));
			size.setDiameter(result.getString(13));
			tire.setSize(size);
			//Tire group info
			if (group == null && !fetchAll) {
				group = new ArticlesGroup();
				group.setCode(result.getInt(14));
				group.setName(result.getString(15));
				VATRate vat = new VATRate();
				vat.setId(result.getInt(16));
				vat.setRate(result.getFloat(17));
				group.setVat(vat);
			}
			else if (group == null) {
				group = GroupsService.getInstance().getArticleGroup(result.getInt(14));
			}
			tire.setGroup(group);

			if (fetchAll) {
				sQuery = "SELECT DOTy.IdDOTu, DOTy.DOT, Liczba FROM DOTyOpon "
						+ "INNER JOIN DOTy ON DOTyOpon.IdDOTu=DOTy.IdDOTu WHERE IdOpony=" + tire.getId() + ";";
				if (iCounter != results.size()) {
					DatabaseManager.getInstance().maintainConnection();
				}
				results = DatabaseManager.getInstance().executeQueryResult(sQuery);
				if (!results.isEmpty()) {
					Map<DOT, Integer> tiresDOTs = new WorkingMap<>();
					DOT currentDOT;
					for (ResultRow rr : results) {
						currentDOT = new DOT(rr.getInt(1), rr.getString(2));
						tiresDOTs.put(currentDOT, rr.getInt(3));
					}
					tire.setTireDOTs(tiresDOTs);
				}
			}

			tires.add(tire);
		}

		return tires;
	}

	public void addTire(Tire tire) throws DatabaseException, SQLException {
		EntityValidator<Tire> validator = new TireValidator();
		validator.validate(tire);
		refreshTireDOTs(tire);

		DatabaseManager.getInstance().startTransaction();

		try {
			String sQuery = "INSERT INTO Opony (KodGrupy, IdBieznika, IdRozmiaru, IndeksNosnosci, IndeksPredkosci, Marza, CenaBrutto) "
					+ "VALUES (" + tire.getGroup().getCode() + ", " + tire.getTread().getId() + ", " + tire.getSize().getId() + ", '" + tire.getLoadIndex() + "', "
					+ "'" + tire.getSpeedIndex() + "', " + tire.getMargin() + ", " + tire.getGrossPrice() + ");";
			DatabaseManager.getInstance().executeQuery(sQuery);

			//Retrieve new tire ID
			sQuery = "SELECT TOP 1 IdOpony FROM Opony ORDER BY IdOpony DESC;";
			tire.setId(DatabaseManager.getInstance().executeQueryResult(sQuery).get(0).getInt(1));

			for (DOT dot : tire.getTireDOTs().keySet()) {
				addTireDOT(tire, dot);
			}
		}
		catch (DatabaseException | SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}

		DatabaseManager.getInstance().commitTransaction();
	}

	public void updateTire(Tire tire) throws DatabaseException, SQLException {
		EntityValidator<Tire> validator = new TireValidator();
		validator.validate(tire);
		refreshTireDOTs(tire);

		DatabaseManager.getInstance().startTransaction();

		try {
			Tire oldTire = getTire(tire.getId());
			String sQuery = "UPDATE Opony SET ";

			if (tire.getGroup().getCode() != oldTire.getGroup().getCode()) {
				sQuery += "KodGrupy=" + tire.getGroup().getCode() + ", ";
			}
			if (tire.getTread().getId() != oldTire.getTread().getId()) {
				sQuery += "IdBieznika=" + tire.getTread().getId() + ", ";
			}
			if (tire.getSize().getId() != oldTire.getSize().getId()) {
				sQuery += "IdRozmiaru=" + tire.getSize().getId() + ", ";
			}
			if (!tire.getLoadIndex().equals(oldTire.getLoadIndex())) {
				sQuery += "IndeksNosnosci=" + tire.getLoadIndex() + ", ";
			}
			if (!tire.getSpeedIndex().equals(oldTire.getSpeedIndex())) {
				sQuery += "IndeksPredkosci='" + tire.getSpeedIndex() + "', ";
			}
			if (tire.getMargin() != oldTire.getMargin()) {
				sQuery += "Marza=" + tire.getMargin() + ", ";
			}
			if (tire.getGrossPrice() != oldTire.getGrossPrice()) {
				sQuery += "CenaBrutto=" + tire.getGrossPrice();
			}

			if (sQuery.lastIndexOf(", ") == sQuery.length() - 2) {
				sQuery = sQuery.substring(0, sQuery.lastIndexOf(", "));
			}

			sQuery += " WHERE IdOpony=" + tire.getId() + ";";
			DatabaseManager.getInstance().executeQuery(sQuery);

			if (tire.getTireDOTs() != null && oldTire.getTireDOTs() != null) {
				for (DOT dot : tire.getTireDOTs().keySet()) {
					if (!oldTire.getTireDOTs().containsKey(dot)) {
						addTireDOT(tire, dot);
					}
					else if (oldTire.getTireDOTs().containsKey(dot) && !oldTire.getTireDOTs().get(dot).equals(tire.getTireDOTs().get(dot))) {
						dot = getDOT(dot.getDot());
						updateTireCount(tire, dot);
					}
				}
				for (DOT dot : oldTire.getTireDOTs().keySet()) {
					if (!tire.getTireDOTs().containsKey(dot)) {
						deleteTireDOT(tire, dot);
					}
				}
			}
			else if (tire.getTireDOTs() != null && oldTire.getTireDOTs() == null) {
				for (DOT dot : tire.getTireDOTs().keySet()) {
					addTireDOT(tire, dot);
				}
			}
			else if (tire.getTireDOTs() == null && oldTire.getTireDOTs() != null) {
				for (DOT dot : oldTire.getTireDOTs().keySet()) {
					deleteTireDOT(tire, dot);
				}
			}
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}

		DatabaseManager.getInstance().commitTransaction();
	}

	public void deleteTire(Tire tire) throws SQLException {
		String sQuery = "DELETE FROM Opony WHERE IdOpony=" + tire.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	private void addTireDOT(Tire tire, DOT dot) throws DatabaseException, SQLException {
		try {
			dot = getDOT(dot.getDot());
		}
		catch (SQLException ex) {
			addDOT(dot);
			dot = getDOT(dot.getDot());
		}
		String sQuery = "INSERT INTO DOTyOpon(IdOpony, IdDOTu, Liczba) VALUES (" + tire.getId() + ", " + dot.getId() + ", " + tire.getTireDOTs().get(dot) + ");";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	private void deleteTireDOT(Tire tire, DOT dot) throws SQLException {
		String sQuery = "DELETE FROM DOTyOpon WHERE IdOpony=" + tire.getId() + " AND IdDOTu=" + dot.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);

		//Remove DOT if no longer used
		sQuery = "DELETE FROM DOTy WHERE IdDOTu=" + dot.getId() + ";";
		try {
			DatabaseManager.getInstance().executeQuery(sQuery);
		}
		catch (SQLException ex) {
			//DOT is probably still in use
		}
	}

	private void updateTireCount(Tire tire, DOT dot) throws SQLException {
		String sQuery = "UPDATE DOTyOpon SET Liczba=" + tire.getTireDOTs().get(dot) + " WHERE IdOpony=" + tire.getId() + " AND IdDOTu=" + dot.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	private void refreshTireDOTs(Tire tire) throws SQLException {
		if (tire.getTireDOTs() != null) {
			for (DOT dot : tire.getTireDOTs().keySet()) {
				if (tire.getTireDOTs().get(dot) == 0) {
					tire.getTireDOTs().remove(dot);
				}
			}
		}
	}
	// </editor-fold>

	// <editor-fold defaultstate="collapsed" desc="TIRE SIZE methods">
	public TireSize getTireSize(int tireSizeId) throws SQLException {
		String sQuery = "SELECT * FROM RozmiaryOpon WHERE IdRozmiaru=" + tireSizeId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tire size with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		TireSize tireSize = new TireSize();
		tireSize.setId(tireSizeId);
		tireSize.setWidth(result.getString(2));
		tireSize.setProfile(result.getString(3));
		tireSize.setDiameter(result.getString(4));

		return tireSize;
	}

	public List<TireSize> getTireSizes() throws SQLException {
		String sQuery = "SELECT * FROM RozmiaryOpon ORDER BY Szerokosc ASC, Profil ASC, Srednica ASC;";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<TireSize> tireSizes = new ArrayList<>();
		TireSize currentSize;

		for (ResultRow result : results) {
			currentSize = new TireSize();
			currentSize.setId(result.getInt(1));
			currentSize.setWidth(result.getString(2));
			currentSize.setProfile(result.getString(3));
			currentSize.setDiameter(result.getString(4));
			tireSizes.add(currentSize);
		}

		return tireSizes;
	}

	public TireSize addTireSize(TireSize tireSize) throws DatabaseException, SQLException {
		EntityValidator<TireSize> validator = new TireSizeValidator();
		validator.validate(tireSize);
		int id;

		DatabaseManager.getInstance().startTransaction();
		try {
			String sQuery = "INSERT INTO RozmiaryOpon(Szerokosc, Profil, Srednica) VALUES ('" + tireSize.getWidth() + "', '" + tireSize.getProfile() + "', '" + tireSize.getDiameter() + "');";
			DatabaseManager.getInstance().executeQuery(sQuery);

			sQuery = "SELECT TOP 1 IdRozmiaru FROM RozmiaryOpon ORDER BY IdRozmiaru DESC;";
			id = DatabaseManager.getInstance().executeQueryResult(sQuery).get(0).getInt(1);
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}
		DatabaseManager.getInstance().commitTransaction();

		return getTireSize(id);
	}

	public void updateTireSize(TireSize tireSize) throws DatabaseException, SQLException {
		EntityValidator<TireSize> validator = new TireSizeValidator();
		validator.validate(tireSize);

		String sQuery = "UPDATE RozmiaryOpon SET Szerokosc='" + tireSize.getWidth() + "', Profil='" + tireSize.getProfile() + "', Srednica='" + tireSize.getDiameter() + "' WHERE IdRozmiaru=" + tireSize.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	public int getTireSizeRemovability(TireSize tireSize) throws SQLException {
		int result = -1;
		String query = "SELECT Sum(DOTyOpon.Liczba) "
				+ "FROM Opony INNER JOIN DOTyOpon ON Opony.IdOpony = DOTyOpon.IdOpony "
				+ "WHERE Opony.IdRozmiaru = " + tireSize.getId() + ";";
		List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
		if (!resultRows.isEmpty()) {
			if (resultRows.get(0).getString(1) != null) {
				if (resultRows.get(0).getFloat(1) == 0.0) {
					result = 0;
				}
				else {
					result = -1;
				}
			}
			else {
				result = 1;
			}
		}
		return result;
	}

	public void deleteTireSize(TireSize tireSize) throws SQLException {
		try {
			DatabaseManager.getInstance().startTransaction();
			String query = "SELECT Opony.IdOpony "
					+ "FROM Opony "
					+ "WHERE Opony.IdRozmiaru = " + tireSize.getId() + ";";
			List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
			for (ResultRow row : resultRows) {
				query = "DELETE FROM Opony "
						+ "WHERE Opony.IdOpony = " + row.getInt(1) + ";";
				DatabaseManager.getInstance().executeQuery(query);
			}
			query = "DELETE FROM RozmiaryOpon "
					+ "WHERE RozmiaryOpon.IdRozmiaru = " + tireSize.getId() + ";";
			DatabaseManager.getInstance().executeQuery(query);
			DatabaseManager.getInstance().commitTransaction();
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}
	}

	// </editor-fold>
	// <editor-fold defaultstate="collapsed" desc="TREAD methods">
	public Tread getTread(int treadId) throws SQLException {
		String sQuery = "SELECT * FROM BieznikiOpon WHERE IdBieznika=" + treadId + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		if (results.isEmpty()) {
			throw new SQLException("Tread with given ID does not exist!");
		}
		ResultRow result = results.get(0);

		Tread tread = new Tread();
		tread.setId(treadId);
		tread.setProducer(ProducersService.getInstance().getProducer(result.getInt(2)));
		tread.setName(result.getString(3));

		return tread;
	}

	public List<Tread> getTreads() throws SQLException {
		String sQuery = "SELECT * FROM BieznikiOpon;";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<Tread> treads = new ArrayList<>();
		Tread currentTread;

		for (ResultRow rr : results) {
			currentTread = new Tread();
			currentTread.setId(rr.getInt(1));
			currentTread.setProducer(ProducersService.getInstance().getProducer(rr.getInt(2)));
			currentTread.setName(rr.getString(3));
			treads.add(currentTread);
		}

		return treads;
	}

	public List<Tread> getTreads(Producer producer) throws SQLException {
		String sQuery = "SELECT * FROM BieznikiOpon WHERE IdProducenta=" + producer.getId() + ";";

		List<ResultRow> results = DatabaseManager.getInstance().executeQueryResult(sQuery);
		List<Tread> treads = new ArrayList<>();
		Tread currentTread;

		for (ResultRow rr : results) {
			currentTread = new Tread();
			currentTread.setId(rr.getInt(1));
			currentTread.setProducer(producer);
			currentTread.setName(rr.getString(3));
			treads.add(currentTread);
		}

		return treads;
	}

	public Tread addTread(Tread tread) throws DatabaseException, SQLException {
		EntityValidator<Tread> validator = new TreadValidator();
		validator.validate(tread);
		int id;

		DatabaseManager.getInstance().startTransaction();
		try {
			String sQuery = "INSERT INTO BieznikiOpon(IdProducenta, Nazwa) VALUES (" + tread.getProducer().getId() + ", '" + tread.getName() + "');";
			DatabaseManager.getInstance().executeQuery(sQuery);

			sQuery = "SELECT TOP 1 IdBieznika FROM BieznikiOpon ORDER BY IdBieznika DESC;";
			id = DatabaseManager.getInstance().executeQueryResult(sQuery).get(0).getInt(1);
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}
		DatabaseManager.getInstance().commitTransaction();

		return getTread(id);
	}

	public void updateTread(Tread tread) throws DatabaseException, SQLException {
		EntityValidator<Tread> validator = new TreadValidator();
		validator.validate(tread);

		String sQuery = "UPDATE BieznikiOpon SET Nazwa='" + tread.getName() + "' WHERE IdBieznika=" + tread.getId() + ";";
		DatabaseManager.getInstance().executeQuery(sQuery);
	}

	public int getTreadRemovability(Tread tread) throws SQLException {
		int result = -1;
		String query = "SELECT SUM(DOTyOpon.Liczba) "
				+ "FROM Opony INNER JOIN DOTyOpon ON Opony.IdOpony = DOTyOpon.IdOpony "
				+ "WHERE Opony.IdBieznika = " + tread.getId() + ";";
		System.out.println("query = " + query);
		List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
		if (!resultRows.isEmpty()) {
			if (resultRows.get(0).getString(1) != null) {
				if (resultRows.get(0).getFloat(1) == 0.0) {
					result = 0;
				}
				else {
					result = -1;
				}
			}
			else {
				query = "SELECT COUNT(Opony.IdOpony) "
						+ "FROM Opony "
						+ "WHERE Opony.IdBieznika = " + tread.getId() + ";";
				resultRows = DatabaseManager.getInstance().executeQueryResult(query);
				if (!resultRows.isEmpty() && resultRows.get(0).getString(1) != null && resultRows.get(0).getInt(1) == 0) {
					result = 1;
				}
				else {
					result = 0;
				}
			}
		}
		return result;
	}

	public void deleteTread(Tread tread) throws SQLException {
		DatabaseManager.getInstance().startTransaction();
		try {
			String query = "SELECT Opony.IdOpony "
					+ "FROM Opony "
					+ "WHERE IdBieznika = " + tread.getId() + ";";
			List<ResultRow> resultRows = DatabaseManager.getInstance().executeQueryResult(query);
			for (ResultRow row : resultRows) {
				query = "DELETE FROM Opony "
						+ "WHERE IdOpony = " + row.getInt(1) + ";";
				DatabaseManager.getInstance().executeQuery(query);
			}
			query = "DELETE FROM BieznikiOpon "
					+ "WHERE BieznikiOpon.IdBieznika = " + tread.getId() + ";";
			DatabaseManager.getInstance().executeQuery(query);
			DatabaseManager.getInstance().commitTransaction();
		}
		catch (SQLException ex) {
			DatabaseManager.getInstance().rollbackTransaction();
			throw ex;
		}
	}
	// </editor-fold>
}
