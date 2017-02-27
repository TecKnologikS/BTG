package fr.tecknologiks.btg;

/**
 * Created by robin on 2/27/2017.
 */

public class ListeCommande {
    public final static String PILLAGE_SELECT = "Travian.Game.RaidList.markAllSlotsOfAListForRaid(%1%, true);";
    public final static String PILLAGE_CLICK = "document.getElementById('%1%').children[0].submit();";
    public final static String TROUPAGE = "document.getElementsByName('%1%')[0].value = parseInt(parseInt(document.getElementsByName('%1%')[0].getParent('div.details').children[5].innerText) / %02%)";
}
