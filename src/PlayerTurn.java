
import core.Hex;
import world.World;
import core.Assets;

public class PlayerTurn {

    // TURN COLORS:
    // 0 - RED
    // 1 - BLUE

    public Hex[][] world;
    public World worldGen;
    public int[] teamMoney = { 10, 10, 10, 10, 10, 10, 10, 10 };
    public int currentturn = 0;
    public int totalplayers = 2;

    public PlayerTurn(Hex[][] world, World worldGen) {
        this.world = world;
        this.worldGen = worldGen;
    }

    public void endTurn(World worldGen) {
        System.out.println("ended turn for player " + currentturn + " going to " + (currentturn + 1));
        setCurrentPlayerMoney(getCurrentPlayerMoney() + getCurrentPlayerEarnings());
        currentturn++;
        if (currentturn == totalplayers) {
            currentturn = 0;
        }
        Hex[] setReady = worldGen.getSpecificHexs(currentturn + worldGen.predefinedstatuses);
        for (int i = 0; i < setReady.length; i++) {
            if (setReady[i].getItem().getItemtype().equals("soldier")) {
                setReady[i].getItem().isReady = true;
                System.out.println(setReady[i].getItem().getItemtype());
            }
        }
    }

    public int getPriceOfItemName(String itemName) {
        if (itemName == "soldier0")
            return Assets.itemPrice[0];
        if (itemName == "soldier1")
            return Assets.itemPrice[1];
        if (itemName == "soldier2")
            return Assets.itemPrice[2];
        if (itemName == "soldier3")
            return Assets.itemPrice[3];

        if (itemName == "tower0")
            return Assets.itemPrice[4];
        if (itemName == "tower1")
            return Assets.itemPrice[5];

        if (itemName == "farm")
            return Assets.itemPrice[6];

        return -1;
    }

    public void undo() {
        // TODO: write undo
    }

    public int getCurrentPlayerEarnings() {
        return worldGen.getSpecificHexs(currentturn + 2).length;
    }

    public int getCurrentPlayerMoney() {
        return teamMoney[currentturn];
    }

    public void setCurrentPlayerMoney(int newMoney) {
        teamMoney[currentturn] = newMoney;
    }
}
