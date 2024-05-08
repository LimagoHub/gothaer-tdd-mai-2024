package de.gothaer.gui.presenter;


import de.gothaer.gui.Euro2DollarRechnerView;
import de.gothaer.model.Euro2DollarRechner;

public class Euro2DollarPresenterImpl implements Euro2DollarPresenter {
	
	private Euro2DollarRechnerView view;
	private Euro2DollarRechner model;
	
	
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getView()
	 */
	@Override
	public Euro2DollarRechnerView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setView(de.gui.IEuro2DollarRechnerView)
	 */
	@Override
	public void setView(Euro2DollarRechnerView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getModel()
	 */
	@Override
	public Euro2DollarRechner getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setModel(de.model.IEuro2DollarRechner)
	 */
	@Override
	public void setModel(Euro2DollarRechner model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#rechnen()
	 */


	/* (Nicht Aufgabe dieser Function den Button zu enablen bzw. disablen)
		Eurowert als String aus der Maske holen
		Eurowert in einen Double wandeln ( Im Fehlerfall -> schreiben Fehlermeldung ins Dollarfeld)
		model aufrufen (Im Fehlerfall -> schreiben Fehlermeldung ins Dollarfeld)
		ergebnis umwandeln in String
		Umgewandeltes Ergenis in die Maske schreiben
	 */
	@Override
	public void onRechnen() {
		try {
			double euro = Double.parseDouble(view.getEuro());
			double dollar = model.calculateEuro2Dollar(euro);
			view.setDollar(String.format("%.2f", dollar));
		} catch (NumberFormatException | NullPointerException e) {
			view.setDollar("Keine Zahl");
		} catch (RuntimeException e) {
			view.setDollar("Fehler im Service");
		}

	}
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#beenden()
	 */
	@Override
	public void onBeenden() {  
		view.close();
	}
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#populateItems()
	 */
	@Override
	public void onPopulateItems() {
		view.setEuro("0");
		view.setDollar("0");
		view.setRechnenEnabled(true);
	}

	@Override
	public void updateRechnenActionState() {


	}

}
