package framework;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Calculations {
	
	  public static String GenerateBirthDate() {

	        GregorianCalendar gc = new GregorianCalendar();

	        int year = randBetween(1950, 1999);

	        gc.set(Calendar.YEAR, year);

	        int dayOfYear = randBetween(1, gc.getActualMaximum(Calendar.DAY_OF_YEAR));

	        gc.set(Calendar.DAY_OF_YEAR, dayOfYear);
	        
	        String DOB = gc.get(Calendar.DAY_OF_MONTH) + "/" + (gc.get(Calendar.MONTH) + 1) + "/" + gc.get(Calendar.YEAR);
	        
	        @SuppressWarnings("unused")
			String DOB2 = null;
	        
	        if (gc.get(Calendar.DAY_OF_MONTH) < 10) {
		        switch((gc.get(Calendar.MONTH) + 1)) {
		        	
		        	case 1:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " January " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 2:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " February " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 3:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " March " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 4:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " April " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 5:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " May " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 6:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " June " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 7:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " July " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 8:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " August " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 9:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " September " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 10:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " October " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 11:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " November " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 12:
		        		 DOB2 = "0"+gc.get(Calendar.DAY_OF_MONTH) + " December " + (gc.get(Calendar.YEAR));
		        		 break;
		        
		        }
	        } else {
	        
		        switch((gc.get(Calendar.MONTH) + 1)) {
		        	
		        	case 1:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " January " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 2:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " February " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 3:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " March " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 4:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " April " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 5:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " May " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 6:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " June " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 7:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " July " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 8:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " August " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 9:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " September " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 10:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " October " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 11:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " November " + (gc.get(Calendar.YEAR));
		        		 break;
		        	case 12:
		        		 DOB2 = gc.get(Calendar.DAY_OF_MONTH) + " December " + (gc.get(Calendar.YEAR));
		        		 break;
		        
		        }
	        }
	        
	       
	        return DOB;

	    }

	    public static int randBetween(int start, int end) {
	        return start + (int)Math.round(Math.random() * (end - start));
	    }
	   
		
		
		
		public static String FutureDate(Integer numberOfDays){
		    SimpleDateFormat formattedDate = new SimpleDateFormat("dd/MM/yyyy");            
		    Calendar c = Calendar.getInstance();        
		    c.add(Calendar.DATE, numberOfDays);  // number of days to add      
		    String date = (String)(formattedDate.format(c.getTime()));
		    return date;
		}
		
		
	    

}
