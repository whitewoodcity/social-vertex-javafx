package cn.net.polyglot.views.mains;

import cn.net.polyglot.views.mains.states.ChartViewState;
import cn.net.polyglot.views.mains.states.MainViewState;
import cn.net.polyglot.views.mains.states.NormalViewState;
import javafx.scene.Node;

/**
 * 消息界面区域上下文
 */
public class MainViewContext {

    private NormalViewState normalView;
    private ChartViewState chartView;

    private MainViewState currentView;

    public MainViewContext(){
        normalView=new NormalViewState(this);
        chartView=new ChartViewState(this);
        setCurrentView(normalView);
    }

    public void setCurrentView(MainViewState currentView) {
        this.currentView = currentView;
    }

    public MainViewState getCurrentView() {
        return currentView;
    }

    public Node getView(){
        return this.currentView.getView();
    }

    public void applyChartView(){
        this.currentView=chartView;
    }

    public void applyNormalView(){
        this.currentView=normalView;
    }
}
