import java.util.ArrayList;
import java.util.Iterator;

public class Prescription extends Order {

	// Dimiourgeitai ena kainourgio antikeimeno typou Recipe.
	
	public Prescription() {
		
		medicines = new ArrayList<Medicine>();
		quantityOfMedicines = new ArrayList<Integer>();
		totalCost = 0;
		
	}
	
	// Prostithetai ena neo farmako stis lista me ta farmaka kai h posotita tou sthn antistoixi thesi sthn lista me tis posothtes.
	// Taytoxrona meiwnetai h diathesimotita tou farmakou apo to iatreio.
	
	public void addMedicineInTheOrder (Medicine orderedMedicine,int quantity) {
		
		// xreiazetai sto gui na ginetai elegxos diathesimotitas
		
		super.addMedicineInTheOrder(orderedMedicine, quantity);
		orderedMedicine.setAvailability(orderedMedicine.getAvailability() - quantity);
	
	}
	
	public void deleteMedicineFronTheOrder(Medicine orderedMedicine) {
	
		Iterator<Medicine> iterator = medicines.iterator();
		int i = 0;
		
		while( iterator.hasNext() ) {
		
		    Medicine medicine = iterator.next();
		    
		    if(medicine.getCode().equals(orderedMedicine.getCode())) {
		        iterator.remove();
		        orderedMedicine.setAvailability(orderedMedicine.getAvailability() + quantityOfMedicines.get(i));
		        quantityOfMedicines.remove(i);
		        i++;
		    }
		}
	}
	
	// Ypologizetai kai epistrefetai to synoliko kostos ths syntaghs.

	public double getTotalCost() {
		
		totalCost = 0;
		
		for(int i = 0;i<medicines.size();i++) 
			totalCost += medicines.get(i).getPrice() * quantityOfMedicines.get(i);
		
		return totalCost;
	}

}