package testCsvReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

import Models.Station;

/**
package testRead;

import java.io.File;

/**
 * @author Clem
 *
 */
public class testReader {

	/**
	 * 
	 */
	public testReader() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
        System.out.println("Hello, World");

        //String fileNameDefined = "resources/hubwaydata_10_12_to_11_13.csv";
        String stations = "resources/stations_10_12_to_11_13.csv";
        // -File class needed to turn stringName to actual file

        ArrayList<Station> stationList = extractStationCSV(stations);

        printMinMaxStations(stationList);        
	}

	private static ArrayList<Station> extractStationCSV(String stations) {
		ArrayList<Station> stationList = new ArrayList<Station>();
        int errorCount = 0, k = 0;
        File file = new File(stations);

        try{
            // -read from filePooped with Scanner class
            Scanner inputStream = new Scanner(file);
            String data = inputStream.nextLine();
            
            // hashNext() loops line-by-line
            while(inputStream.hasNextLine()){
                //read single line, put in string
                data = inputStream.nextLine();
                System.out.println(data);

                String[] splited = data.split(",");
                
                Station station = new Station();

                station.id = Integer.parseInt(splited[0]);
                station.terminal = (splited[1]);
                station.station = (splited[2]);
                station.municipality = (splited[3]);
                station.lat = Double.parseDouble(splited[4]);
                station.lng = Double.parseDouble(splited[5]);
                
                if (splited.length <= 6)
                {
                errorCount++;	
                System.out.println("Error  missing dates!");
                } else {
                    station.nb_docks = (splited[6]);
                station.install_date= new Date((splited[7])) ;
                station.last_day = new Date((splited[8])) ;
                stationList.add(station);
                }
                k++; if(k>500) break;
            }
            inputStream.close();

            System.out.println("stationList.size() = " + stationList.size());
            System.out.println("errorCount = " + errorCount);
         
        }
        catch (FileNotFoundException e){

            e.printStackTrace();
        }
		return stationList;
	}

	private static void printMinMaxStations(ArrayList<Station> stationList) {
		Double minDist = Double.MAX_VALUE;
		Double maxDist = Double.MIN_VALUE;            
		Station maxStationStart = null, maxStationDest = null, minStationStart = null, minStationDest = null; 
				
		for (Iterator<Station> itStart = stationList.iterator(); itStart.hasNext();) {
			Station start = (Station) itStart.next();


			for (Iterator<Station> itDest = stationList.iterator(); itDest.hasNext();) {
				Station dest = (Station) itDest.next();

				if (start.station.equalsIgnoreCase(dest.station))
					continue;
				
//					Double diffLat = Math.abs(dest.lat-start.lat);
//					Double diffLng = Math.abs(dest.lng-start.lng);
//					
//					Double dist = Math.sqrt(diffLat*diffLat + diffLng*diffLng);
//					
				Double dist = distFrom(dest.lat, dest.lng, start.lat, start.lng);
														
				if (dist < minDist)
				{
					minDist = dist;
					minStationStart = start;
					minStationDest = dest;
				}
				
				if (dist > maxDist)
				{
					maxDist = dist;
					maxStationStart = start;
					maxStationDest = dest;
				}
			}
		}            

		System.out.println("minStationStart.station() = " + minStationStart.station + ", " + minStationStart.municipality);
		System.out.println("minStationDest.station() = " + minStationDest.station + ", " + minStationDest.municipality);
		System.out.println("minDist() = " + minDist);
		
		System.out.println("maxStationStart.station() = " + maxStationStart.station + ", " + maxStationStart.municipality);
		System.out.println("maxStationDest.station() = " + maxStationDest.station + ", " + maxStationDest.municipality);
		System.out.println("maxDist() = " + maxDist);
	}

	// stolen from the internets.
	public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
	    double earthRadius = 3958.75;
	    double dLat = Math.toRadians(lat2-lat1);
	    double dLng = Math.toRadians(lng2-lng1);
	    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
	               Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
	               Math.sin(dLng/2) * Math.sin(dLng/2);
	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
	    double dist = earthRadius * c;

	    int meterConversion = 1609;

	    return (double) (dist * meterConversion);
	    }
	
}
