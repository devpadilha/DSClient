package augustopadilha.clientdistributedsystems.models;

import augustopadilha.clientdistributedsystems.views.ViewFactory;

public class Model {
    private final ViewFactory viewFactory = new ViewFactory();
    private static final Model instance = new Model();

    private Model(){
    }

    public static Model getInstance() {
        return instance;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }
}
