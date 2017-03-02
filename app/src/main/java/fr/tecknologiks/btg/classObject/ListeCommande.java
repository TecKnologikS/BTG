package fr.tecknologiks.btg.classObject;

/**
 * Created by robin on 2/27/2017.
 */

public class ListeCommande {
    public final static String PILLAGE_SELECT = "Travian.Game.RaidList.markAllSlotsOfAListForRaid(%1%, true);";
    public final static String PILLAGE_CLICK = "document.getElementById('list%1%').children[0].submit();";
    public final static String TROUPAGE = "document.getElementsByName('t%1%')[0].value = parseInt(parseInt(document.getElementsByName('t%1%')[0].getParent('div.details').children[5].innerText) / 2);";
    public final static String TROUPAGE_CLICK = "document.snd.s1.click();";
    public final static String BUILDIT = "document.getElementsByClassName('green build')[0].click();";
}
