package com.axelia.bakingapp.ui.stepdetail;

import androidx.lifecycle.ViewModel;
import com.axelia.bakingapp.data.local.model.Step;
import com.axelia.bakingapp.util.SingleLiveEvent;

import java.util.List;

import timber.log.Timber;


public class StepDetailViewModel extends ViewModel {

    private List<Step> stepsList;
    private int currentPosition;
    private final SingleLiveEvent<Step> navigateToStepDetail = new SingleLiveEvent<>();

    public void init(List<Step> steps, int position) {
        stepsList = steps;
        setCurrentPosition(position);
    }

    public void setCurrentPosition(int position) {
        currentPosition = position;
        navigateToCurrentStep();
    }

    public void nextStep() {
        currentPosition++;
        navigateToCurrentStep();
    }

    public void previousStep() {
        currentPosition--;
        navigateToCurrentStep();
    }

    public boolean hasNext() {
        return currentPosition < stepsList.size() - 1;
    }

    public boolean hasPrevious() {
        return currentPosition > 0;
    }

    private void navigateToCurrentStep() {
        navigateToStepDetail.setValue(stepsList.get(currentPosition));
    }

    public SingleLiveEvent<Step> getNavigateToStepDetail() {
        return navigateToStepDetail;
    }

}
