package org.sahaj.parking.control;

import org.sahaj.parking.utils.Constants;
import org.sahaj.parking.data.ModelType;
import org.sahaj.parking.model.StadiumModel;
import org.sahaj.parking.model.MallModel;
import org.sahaj.parking.model.Model;
import org.sahaj.parking.model.AirportModel;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Model factory.
 */
public class ModelFactory {

    /**
     * List Available models for parking places.
     */
    Map<ModelType, Model> availableModels;

    /**
     * Returns model object's instance for given model type.
     *
     * @param modelType the model type
     * @return the model object
     */
    public Model getModel(ModelType modelType) {
        return availableModels.get(modelType);
    }

    /**
     * Instantiates a new Model factory.
     * Add all supported models in model factory.
     */
    public ModelFactory() {
        availableModels = new HashMap<>();
        availableModels.put(ModelType.Stadium, new StadiumModel(Constants.stadiumModelFile));
        availableModels.put(ModelType.Airport, new AirportModel(Constants.airportModelFile));
        availableModels.put(ModelType.Mall, new MallModel(Constants.mallModelFile));
    }
}