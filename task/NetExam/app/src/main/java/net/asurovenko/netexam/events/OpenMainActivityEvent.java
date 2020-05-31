package net.asurovenko.netexam.events;


public class OpenMainActivityEvent {
    public static final String CURRENT_TAB = "CURRENT_TAB";
    private int currentTab;

    public OpenMainActivityEvent(int currentTab) {
        this.currentTab = currentTab;
    }

    public int getCurrentTab() {
        return currentTab;
    }
}
