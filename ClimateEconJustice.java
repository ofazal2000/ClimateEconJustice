package climate;

import java.util.ArrayList;

/**
 * This class contains methods which perform various operations on a layered 
 * linked list structure that contains USA communitie's Climate and Economic information.
 * 
 * @author Navya Sharma
 */

public class ClimateEconJustice {

    private StateNode firstState;
    
    /*
    * Constructor
    * 
    * **** DO NOT EDIT *****
    */
    public ClimateEconJustice() {
        firstState = null;
    }

    /*
    * Get method to retrieve instance variable firstState
    * 
    * @return firstState
    * 
    * **** DO NOT EDIT *****
    */ 
    public StateNode getFirstState () {
        // DO NOT EDIT THIS CODE
        return firstState;
    }

    /**
     * Creates 3-layered linked structure consisting of state, county, 
     * and community objects by reading in CSV file provided.
     * 
     * @param inputFile, the file read from the Driver to be used for
     * @return void
     * 
     * **** DO NOT EDIT *****
     */
    public void createLinkedStructure ( String inputFile ) {
        
        // DO NOT EDIT THIS CODE
        StdIn.setFile(inputFile);
        StdIn.readLine();
        
        // Reads the file one line at a time
        while ( StdIn.hasNextLine() ) {
            // Reads a single line from input file
            String line = StdIn.readLine();
            // IMPLEMENT these methods
            addToStateLevel(line);
            addToCountyLevel(line);
            addToCommunityLevel(line);
        }
    }

    /*
    * Adds a state to the first level of the linked structure.
    * Do nothing if the state is already present in the structure.
    * 
    * @param inputLine a line from the input file
    */
    public void addToStateLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String stateName = data[2];
        StateNode currentState = firstState;
        while (currentState != null) {
            if(currentState.name.equals(stateName)){
                return;
            }
            if(currentState.next == null){
                break;
            }
            currentState = currentState.next;
        }
        if (firstState == null){
            firstState = new StateNode(stateName, null, null);
        } else {
            currentState.next = new StateNode(stateName, null, null);
        }
    }

    /*
    * Adds a county to a state's list of counties.
    * 
    * Access the state's list of counties' using the down pointer from the State class.
    * Do nothing if the county is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCountyLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String stateName = data[2];
        String countyName = data[1];
        StateNode currentState = firstState;
        CountyNode currentCounty;

        while (currentState != null) {
            if(currentState.name.equals(stateName)){
                currentCounty = currentState.down;
                while(currentCounty != null){
                    if(currentCounty.name.equals(countyName)){
                        return;
                    }
                    if(currentCounty.next == null){
                        break;
                    }
                    currentCounty = currentCounty.next;
                }
                if(currentState.down == null){
                    currentState.down = new CountyNode(countyName, null, null);
                } else{
                    currentCounty.next = new CountyNode(countyName,null,null);
                }
            }
            currentState = currentState.next;
        }
    }


    /*
    * Adds a community to a county's list of communities.
    * 
    * Access the county through its state
    *      - search for the state first, 
    *      - then search for the county.
    * Use the state name and the county name from the inputLine to search.
    * 
    * Access the state's list of counties using the down pointer from the StateNode class.
    * Access the county's list of communities using the down pointer from the CountyNode class.
    * Do nothing if the community is already present in the structure.
    * 
    * @param inputFile a line from the input file
    */
    public void addToCommunityLevel ( String inputLine ) {

        String[] data = inputLine.split(",");
        String stateName = data[2];
        String countyName = data[1];
        String communityName = data[0];
        double africanAmericanPercentage = Double.parseDouble(data[3]);
        double nativePercentage = Double.parseDouble(data[4]);
        double asianPercentage = Double.parseDouble(data[5]);
        double whitePercentage = Double.parseDouble(data[8]);
        double hispanicPercentage = Double.parseDouble(data[9]);
        String disadvantaged = data[19];
        double pmLevel = Double.parseDouble(data[49]);
        double floodChance = Double.parseDouble(data[37]);
        double povertyLine = Double.parseDouble(data[121]);
        StateNode currentState = firstState;
        CountyNode currentCounty;
        CommunityNode currentCommunity;
        Data communityData = new Data(africanAmericanPercentage, nativePercentage, asianPercentage, whitePercentage, hispanicPercentage, disadvantaged, pmLevel, floodChance, povertyLine);
        while (currentState != null) {
            if(currentState.name.equals(stateName)){
                currentCounty = currentState.down;
                while(currentCounty != null){
                    if(currentCounty.name.equals(countyName)){
                        currentCommunity = currentCounty.down;
                        while(currentCommunity != null){
                            if(currentCommunity.name.equals(communityName)){
                                return;
                            }
                            if(currentCommunity.next == null){
                                break;
                            }
                            currentCommunity = currentCommunity.next;
                        }
                        if(currentCounty.down == null){
                            currentCounty.down = new CommunityNode(communityName,null,communityData);
                        } else{
                            currentCommunity.next = new CommunityNode(communityName, null, communityData);
                        }

                    }
                    currentCounty = currentCounty.next;
                }
            }
            currentState = currentState.next;
            
        }
    }


    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int disadvantagedCommunities ( double userPrcntage, String race ) {

        int count = 0;
    StateNode currentState = firstState;
    while (currentState != null) {
        CountyNode currentCounty = currentState.getDown();
        while (currentCounty != null) {
            CommunityNode currentCommunity = currentCounty.getDown();
            while (currentCommunity != null) {
                Data data = currentCommunity.info;
                double racePercentage = 0;
                if (race.equals("African American")) {
                    racePercentage = data.prcntAfricanAmerican * 100;
                } else if (race.equals("Native American")) {
                    racePercentage = data.prcntNative * 100;
                } else if (race.equals("Asian American")) {
                    racePercentage = data.prcntAsian * 100;
                } else if (race.equals("White American")) {
                    racePercentage = data.prcntWhite * 100;
                } else if (race.equals("Hispani American")) {
                    racePercentage = data.prcntHispanic * 100;
                }
                if (Boolean.valueOf(data.disadvantaged) && racePercentage >= userPrcntage) {
                    count++;
                }
                currentCommunity = currentCommunity.getNext();
            }
            currentCounty = currentCounty.getNext();
        }
        currentState = currentState.getNext();
    }
    return count;
}

    /**
     * Given a certain percentage and racial group inputted by user, returns
     * the number of communities that have that said percentage or more of racial group  
     * and are identified as non disadvantaged
     * 
     * Percentages should be passed in as integers for this method.
     * 
     * @param userPrcntage the percentage which will be compared with the racial groups
     * @param race the race which will be returned
     * @return the amount of communities that contain the same or higher percentage of the given race
     */
    public int nonDisadvantagedCommunities ( double userPrcntage, String race ) {

    int count = 0;
    StateNode currentState = firstState;
    while (currentState != null) {
        CountyNode currentCounty = currentState.getDown();
        while (currentCounty != null) {
            CommunityNode currentCommunity = currentCounty.getDown();
            while (currentCommunity != null) {
                Data data = currentCommunity.info;
                double racePercentage = 0;
                if (race.equals("African American")) {
                    racePercentage = data.prcntAfricanAmerican * 100;
                } else if (race.equals("Native American")) {
                    racePercentage = data.prcntNative * 100;
                } else if (race.equals("Asian American")) {
                    racePercentage = data.prcntAsian * 100;
                } else if (race.equals("White American")) {
                    racePercentage = data.prcntWhite * 100;
                } else if (race.equals("Hispani American")) {
                    racePercentage = data.prcntHispanic * 100;
                }
                if (Boolean.valueOf(data.disadvantaged) == false && racePercentage >= userPrcntage) {
                        count++;
                }
                currentCommunity = currentCommunity.getNext();
            }
            currentCounty = currentCounty.getNext();
        }
        currentState = currentState.getNext();
    }
    return count; // replace this line
}

        


    /** 
     * Returns a list of states that have a PM (particulate matter) level
     * equal to or higher than value inputted by user.
     * 
     * @param PMlevel the level of particulate matter
     * @return the States which have or exceed that level
     */ 
    public ArrayList<StateNode> statesPMLevels ( double PMlevel ) {

        ArrayList<StateNode> states = new ArrayList<>();
        StateNode currentState = firstState;
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            boolean stateAdded = false;
            while (currentCounty != null && !stateAdded) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null && !stateAdded) {
                    Data data = currentCommunity.info;
                    if (data.PMlevel >= PMlevel) {
                        states.add(currentState);
                        stateAdded = true;
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
        return states; // replace this line
    }

    /**
     * Given a percentage inputted by user, returns the number of communities 
     * that have a chance equal to or higher than said percentage of
     * experiencing a flood in the next 30 years.
     * 
     * @param userPercntage the percentage of interest/comparison
     * @return the amount of communities at risk of flooding
     */
    public int chanceOfFlood ( double userPercntage ) {

        int count = 0;
        StateNode currentState = firstState;
        while (currentState != null) {
            CountyNode currentCounty = currentState.getDown();
            while (currentCounty != null) {
                CommunityNode currentCommunity = currentCounty.getDown();
                while (currentCommunity != null) {
                    Data data = currentCommunity.info;
                    if (data.chanceOfFlood >= userPercntage) {
                        count++;
                    }
                    currentCommunity = currentCommunity.getNext();
                }
                currentCounty = currentCounty.getNext();
            }
            currentState = currentState.getNext();
        }
        return count; // replace this line
    }

    /** 
     * Given a state inputted by user, returns the communities with 
     * the 10 lowest incomes within said state.
     * 
     *  @param stateName the State to be analyzed
     *  @return the top 10 lowest income communities in the State, with no particular order
    */
    public ArrayList<CommunityNode> lowestIncomeCommunities ( String stateName ) {

        ArrayList<CommunityNode> lowestIncomeCommunities = new ArrayList<>();
        StateNode currentState = firstState;
        while (currentState != null) {
            if (currentState.name.equals(stateName)) {
                CountyNode currentCounty = currentState.getDown();
                while (currentCounty != null) {
                    CommunityNode currentCommunity = currentCounty.getDown();
                    while (currentCommunity != null) {
                        if (lowestIncomeCommunities.size() < 10) {
                            lowestIncomeCommunities.add(currentCommunity);
                        } else {
                            // Find the community with the highest income in the list
                            CommunityNode highestIncomeCommunity = lowestIncomeCommunities.get(0);
                            for (CommunityNode community : lowestIncomeCommunities) {
                                if (community.info.prcntPovertyLine < highestIncomeCommunity.info.prcntPovertyLine) {
                                    highestIncomeCommunity = community;
                                }
                            }
                            // Replace it with the current community if the current community has a lower income
                            if (currentCommunity.info.prcntPovertyLine > highestIncomeCommunity.info.prcntPovertyLine) {
                                lowestIncomeCommunities.set(lowestIncomeCommunities.indexOf(highestIncomeCommunity), currentCommunity);
                            }
                        }
                        currentCommunity = currentCommunity.getNext();
                    }
                    currentCounty = currentCounty.getNext();
                }
                break;
            }
            currentState = currentState.getNext();
        }
        return lowestIncomeCommunities;
   }
}