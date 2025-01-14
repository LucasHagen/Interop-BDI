/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interop.stratigraphic.model;

import interop.model.IDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Luan
 */
public class StratigraphicDescription implements IDescription, Comparable<StratigraphicDescription> {

    private String wellName;
    private String filePath;
    private float topMeasure;
    private float bottomMeasure;

    private List<DepositionalFacies> faciesList;

    /**
     * @return the faciesList
     */
    public List<DepositionalFacies> getFaciesList() {

        return faciesList;
    }

    public void addFacies(DepositionalFacies df) {
        if (faciesList == null)
            faciesList = new ArrayList<>();

        this.faciesList.add(df);
    }

    /**
     * @return the wellName
     */
    public String getWellName() {
        return wellName;
    }

    /**
     * @param wellName the wellName to set
     */
    public void setWellName(String wellName) {
        this.wellName = wellName;
    }

    /**
     * @return the file path, if loaded from a XML file, or null
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * @param filePath File path
     */
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    /**
     * @return the topMeasure
     */
    public float getTopMeasure() {
        return topMeasure;
    }

    /**
     * @param topMeasure the topMeasure to set
     */
    public void setTopMeasure(float topMeasure) {
        this.topMeasure = topMeasure;
    }

    /**
     * @return the bottomMeasure
     */
    public float getBottomMeasure() {
        return bottomMeasure;
    }

    /**
     * @param bottomMeasure the bottomMeasure to set
     */
    public void setBottomMeasure(float bottomMeasure) {
        this.bottomMeasure = bottomMeasure;
    }

    /**
     * Compares two StratigraphicDescriptions according to their depths.
     *
     * @param desc StratigraphicDescription to compare to
     * @return Int: -1, this comes before desc
     *              0,  both are at exact same depth
     *              1,  this comes after desc
     */
    @Override
    public int compareTo(StratigraphicDescription desc) {
        float center1 = (bottomMeasure + topMeasure) / 2;
        float center2 = (desc.bottomMeasure + desc.topMeasure) / 2;

        return Float.compare(center1, center2);
    }
}
