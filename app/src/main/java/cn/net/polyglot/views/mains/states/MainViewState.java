package cn.net.polyglot.views.mains.states;

import cn.net.polyglot.views.mains.MainViewContext;
import javafx.scene.Node;

/**
 * 消息主面板状态接口
 */
public abstract class MainViewState {

    protected MainViewContext context;

    public MainViewState(MainViewContext context){
        this.context=context;
    }

    public abstract Node getView();

}
