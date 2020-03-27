import java.util.Scanner;

class Carriage {
    String carName;
    Carriage nextCar;
    Carriage prevCar;

    Carriage()
    {
        carName = "";
        nextCar = null;
        prevCar = null;
    }

    Carriage(String _name)
    {
        carName = _name;
        nextCar = null;
        prevCar = null;
    }
}

class Loco {
    String locoName;
    Carriage firstCar;
    Carriage lastCar;
    Loco nextLoco;

    Loco()
    {
        locoName = "";
        firstCar = null;
        lastCar = null;
        nextLoco = null;
    }

    Loco(String _name, String _carriageName)
    {
        locoName = _name;
        firstCar = new Carriage(_carriageName);
        lastCar = firstCar;
    }
}

class TrainList {
    Loco firstLoco;

    TrainList() 
    {
        firstLoco = null;
    }

    void newLoco(String locoName, String carriageName)
    {
        if(firstLoco == null)
            firstLoco = new Loco(locoName, carriageName);
        else
        {
            Loco temp = firstLoco;
            while(temp.nextLoco != null)
                temp = temp.nextLoco;
        
            temp.nextLoco = new Loco(locoName, carriageName);
        }
    }

    void addFirst(String locoName, String carriageName)
    {
        Loco temp = firstLoco;

        while(!temp.locoName.equals(locoName))
            temp = temp.nextLoco;

        Carriage tempCar = temp.firstCar;

        if(tempCar.prevCar == null)
        {
            temp.firstCar = new Carriage(carriageName);
            temp.firstCar.nextCar = tempCar;
            tempCar.prevCar = temp.firstCar;
        }
        else
        {
            temp.firstCar = new Carriage(carriageName);
            temp.firstCar.prevCar = tempCar;
            tempCar.nextCar = temp.firstCar;
        }
    }

    void addLast(String locoName, String carriageName)
    {
        Loco temp = firstLoco;

        while(!temp.locoName.equals(locoName))
            temp = temp.nextLoco;

        Carriage newCar = new Carriage(carriageName);

        if(temp.lastCar.nextCar == null)
        {
            temp.lastCar.nextCar = newCar;
            newCar.prevCar = temp.lastCar;
            temp.lastCar = newCar;
        }
        else
        {
            temp.lastCar.prevCar = newCar;
            newCar.nextCar = temp.lastCar;
            temp.lastCar = newCar;
        }
    }

    void remFirst(String locoName1, String locoName2)
    {
        if(locoName1.equals(firstLoco.locoName))
        {
            if(firstLoco.firstCar.equals(firstLoco.lastCar)) //jeden wagon
                firstLoco.locoName = locoName2;
            else
            {
                Carriage tempCar = firstLoco.firstCar;
                if(firstLoco.firstCar.prevCar == null)
                    firstLoco.firstCar = firstLoco.firstCar.nextCar;
                else
                    firstLoco.firstCar = firstLoco.firstCar.prevCar;

                if(firstLoco.firstCar.prevCar != null && firstLoco.firstCar.prevCar.equals(tempCar))
                    firstLoco.firstCar.prevCar = null;
                else
                    firstLoco.firstCar.nextCar = null;

                this.newLoco(locoName2, tempCar.carName);
            }
        }
        else
        {
            Loco temp = firstLoco;

            while(!temp.nextLoco.locoName.equals(locoName1))
                temp = temp.nextLoco;

            if(temp.nextLoco.firstCar.equals(temp.nextLoco.lastCar)) //jeden wagon
                temp.nextLoco.locoName = locoName2;
            else
            {
                Carriage tempCar = temp.nextLoco.firstCar;
                if(temp.nextLoco.firstCar.prevCar == null)
                    temp.nextLoco.firstCar = temp.nextLoco.firstCar.nextCar;
                else
                    temp.nextLoco.firstCar = temp.nextLoco.firstCar.prevCar;   

                if(temp.nextLoco.firstCar.prevCar != null && temp.nextLoco.firstCar.prevCar.equals(tempCar))
                    temp.nextLoco.firstCar.prevCar = null;
                else
                    temp.nextLoco.firstCar.nextCar = null;

                this.newLoco(locoName2, tempCar.carName);
            }
        }
    }

    void remLast(String locoName1, String locoName2)
    {
        if(locoName1.equals(firstLoco.locoName))
        {
            if(firstLoco.firstCar.equals(firstLoco.lastCar)) //jeden wagon
                firstLoco.locoName = locoName2;
            else
            {
                Carriage tempCar = firstLoco.lastCar;
                if(firstLoco.lastCar.nextCar == null)
                    firstLoco.lastCar = firstLoco.lastCar.prevCar;
                else
                    firstLoco.lastCar = firstLoco.lastCar.nextCar;
                
                if(firstLoco.lastCar.prevCar != null && firstLoco.lastCar.prevCar.equals(tempCar))
                    firstLoco.lastCar.prevCar = null;
                else
                    firstLoco.lastCar.nextCar = null;

                this.newLoco(locoName2, tempCar.carName);
            }
        }
        else
        {   
            Loco temp = firstLoco;

            while(!temp.nextLoco.locoName.equals(locoName1))
                temp = temp.nextLoco;

            if(temp.nextLoco.firstCar.equals(temp.nextLoco.lastCar)) //jeden wagon
                temp.nextLoco.locoName = locoName2;
            else
            {
                Carriage tempCar = temp.nextLoco.lastCar;

                if(temp.nextLoco.lastCar.nextCar == null)
                    temp.nextLoco.lastCar = temp.nextLoco.lastCar.prevCar;
                else
                    temp.nextLoco.lastCar = temp.nextLoco.lastCar.nextCar;

                if(temp.nextLoco.lastCar.prevCar != null && temp.nextLoco.lastCar.prevCar.equals(tempCar))
                    temp.nextLoco.lastCar.prevCar = null;
                else
                    temp.nextLoco.lastCar.nextCar = null;

                this.newLoco(locoName2, tempCar.carName);
            }
        }
    }

    void connect(String locoName1, String locoName2)
    {
        Loco temp1 = firstLoco;

        while(!temp1.locoName.equals(locoName1))
            temp1 = temp1.nextLoco;


        if(locoName2.equals(firstLoco.locoName))
        {
            Carriage tempCar = firstLoco.firstCar;

            if(firstLoco.firstCar.prevCar == null)
                tempCar.prevCar = temp1.lastCar;
            else
                tempCar.nextCar = temp1.lastCar;
    
            if(temp1.lastCar.nextCar == null)
                temp1.lastCar.nextCar = tempCar;
            else
                temp1.lastCar.prevCar = tempCar;
            
            temp1.lastCar = firstLoco.lastCar;
            firstLoco = firstLoco.nextLoco;
        }
        else
        {
            Loco temp2 = firstLoco;

            while(!temp2.nextLoco.locoName.equals(locoName2))
                temp2 = temp2.nextLoco;

            Carriage tempCar = temp2.nextLoco.firstCar;

            if(temp2.nextLoco.firstCar.prevCar == null)
                tempCar.prevCar = temp1.lastCar;
            else
                tempCar.nextCar = temp1.lastCar;

            if(temp1.lastCar.nextCar == null)
                temp1.lastCar.nextCar = tempCar;
            else
                temp1.lastCar.prevCar = tempCar;

            temp1.lastCar = temp2.nextLoco.lastCar;
            temp2.nextLoco = temp2.nextLoco.nextLoco;
        }
    }

    void reverse(String locoName)
    {
        Loco temp = firstLoco;

        while(!temp.locoName.equals(locoName))
        {
            temp = temp.nextLoco;
        }

        Carriage tempCar = temp.firstCar;
        temp.firstCar = temp.lastCar;
        temp.lastCar = tempCar;
    }

    void print(String locoName)
    {
        Loco temp = firstLoco;

        while(!temp.locoName.equals(locoName))
            temp = temp.nextLoco;

        System.out.print(temp.locoName + ": ");

        Carriage tempCar = temp.firstCar;
        Carriage tempPrevCar = null;
        boolean dir = false;

        System.out.print(temp.firstCar.carName + " ");

        while(!tempCar.equals(temp.lastCar))
        {
            if(tempCar.nextCar != null && dir == false && !tempCar.nextCar.equals(tempPrevCar))
            {
                tempPrevCar = tempCar;
                tempCar = tempCar.nextCar;
                System.out.print(tempCar.carName + " ");
            }
            else if(!tempCar.prevCar.equals(tempPrevCar))
                dir = true;


            while(tempCar.prevCar != null && dir == true)
            {
                if(tempCar.prevCar.prevCar != null && tempCar.prevCar.prevCar.equals(tempCar))
                    dir = false;

                tempCar = tempCar.prevCar;
                System.out.print(tempCar.carName + " ");
            }
        }
        System.out.println();
    } 
}


public class Source {
    public static void main(String[] args) {
        
        Scanner in = new Scanner(System.in);

        int sets = in.nextInt();

        while(sets-- > 0)
        {
            TrainList list = new TrainList();

            int operations = in.nextInt();

            while(operations-- > 0)
            {
                String instruction = in.next();

                if(instruction.equals("New"))
                {
                    String locoName = in.next();
                    String carriageName = in.next();
    
                    list.newLoco(locoName, carriageName);
                }
                else if(instruction.equals("InsertFirst"))
                {
                    String locoName = in.next();
                    String carriageName = in.next();

                    list.addFirst(locoName, carriageName);
                }
                else if(instruction.equals("InsertLast"))
                {
                    String locoName = in.next();
                    String carriageName = in.next();

                    list.addLast(locoName, carriageName);
                }
                else if(instruction.equals("Display"))
                {
                    String locoName = in.next();
                    list.print(locoName);
                }
                else if(instruction.equals("Reverse"))
                {
                    String locoName = in.next();

                    list.reverse(locoName);
                }
                else if(instruction.equals("Union"))
                {
                    String locoName1 = in.next();
                    String locoName2 = in.next();

                    list.connect(locoName1, locoName2);
                }
                else if(instruction.equals("DelFirst"))
                {
                    String locoName1 = in.next();
                    String locoName2 = in.next();

                    list.remFirst(locoName1, locoName2);
                }
                else if(instruction.equals("DelLast"))
                {
                    String locoName1 = in.next();
                    String locoName2 = in.next();

                    list.remLast(locoName1, locoName2);
                }
            }       
        }
        in.close();
    }
}
