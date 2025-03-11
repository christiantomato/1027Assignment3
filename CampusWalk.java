//class that will contain the algorithm to get us to MiddleSex!
public class CampusWalk {
    //provide the map that we will traverse
    private Map map;

    //constructor
    public CampusWalk(String filename, boolean showMap) {
        //try to initialize the map object with the given filename
        try {
            this.map = new Map(filename);
        }
        catch (Exception e) {
            //print out simple message
            System.out.println("ERROR DETECTED!!!");
        }
        //if showMap is true
        if(showMap) {
            //then show map
            this.map.showGUI();
        }
    }

    //counts and returns the number of geese in neighbouring hexagons
    public int neighbourGooseCount(Hexagon cell) {
        //count var
        int count = 0;
        //look at all the neighbouring cells
        for(int i = 0; i < 6; i++) {
            if(cell.getNeighbour(i) == null) {
                continue;
            }
            if(cell.getNeighbour(i).isGooseCell()) {
                //count up
                count++;
            }
        }
        return count;
    }

    //determines the best hexagon to move to based on specified conditions
    public Hexagon findBest(Hexagon cell) {
        //our candidates are up to 6 neighbouring cells
        //keep track of the candidate cells. if it becomes bad, make it null
        Hexagon[] candidates = new Hexagon[6];
        for(int i = 0; i < 6; i++) {
            //quickly just check if we are beside the end since this is an easy case
            if(candidates[i].isEnd()) {
                //this is where we wanna go right here
                return candidates[i];
            }
            //candidates cannot be goose cells
            if(candidates[i].isGooseCell()) {
                //if its bad set it as null
                candidates[i] = null;
                continue;
            }
            //candidates cannot have a goose neighbour count of 3 or more (exception of end cell)
            if(this.neighbourGooseCount(candidates[i]) >= 3 && !candidates[i].isEnd()) {
                //bad cell set it as null
                 candidates[i] = null;
                 continue;
             }
             //candidates cannot be a marked cell (one that has been walked on)
             if(candidates[i].isMarked()) {
                //bad cell set as null
                candidates[i] = null;
                continue;
             }
        }

        //the next cell is based on the following ordered rules.
        /*
        1. end cell (already checked)
        2. book cell
        3. grass cell
        4. snow cell
        5. return null
         */ 

         //2. lets look at the remaining candidates and check book cells
        for(int i = 0; i < 6; i++) {
            //skip over the null cases
            if(candidates[i] == null) {
                //bad
                continue;
            }
            //if theres a book cell lets go there
            if(candidates[i].isBookCell()) {
                //this is our guy
                return candidates[i];
            }
        }

        //3. if no books look for a grass cell with lowest goose neighbour count
        //set sentinel values
        int bestGrassCellIndex = 69;
        int lowestGooseCount = 7;
        for(int i = 0; i < 6; i++) {
            //skip over null
            if(candidates[i] == null) {
                //bad
                continue;
            }
            //if grass cell count goose neighbours and record index with least amount
            if(candidates[i].isGrassCell()) {
                if(this.neighbourGooseCount(candidates[i]) < lowestGooseCount) {
                    //set the new lowest amount
                    lowestGooseCount = this.neighbourGooseCount(candidates[i]);
                    bestGrassCellIndex = i;
                }   
            }
        }
        //check if we found a grass cell now
        if(bestGrassCellIndex != 69) {
            //we found something thats our guy
            return candidates[bestGrassCellIndex];
        }

        //4. if we found no grass cells lets try going to a snow cell
        for(int i = 0; i < 6; i++) {
            //skip over the null cases
            if(candidates[i] == null) {
                //bad
                continue;
            }
            //if theres a book cell lets go there
            if(candidates[i].isSnowCell()) {
                //this is our guy
                return candidates[i];
            }
        }

        //5. wow that was long but we didn't find anything somehow
        return null;
    }
}
