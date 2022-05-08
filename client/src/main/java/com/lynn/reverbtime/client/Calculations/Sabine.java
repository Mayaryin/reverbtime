package com.lynn.reverbtime.client.Calculations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/*
Class containing implementation of Sabine formula for reverberation time: T = k* V/A
*/

public class Sabine {

    private Double volume;      //volume of the room
    private double k = 0.161;   // constant coefficient for roon dimensions in meters,  k = 0.161
    private double a;           //average absorption coefficient
    private double t;           // reverberation time
    private List<Double> boundarySurfacaceAreas;  //areas of each wall
    private List<Double> absorptionCoefficients;  //abs coeff of each wall


    public Sabine (Double v,  List<Double> bsa, List<Double> abs){
        this.volume = v;
        this.boundarySurfacaceAreas = bsa;
        this.absorptionCoefficients = abs;
    }

    public double getT() {
        return t;
    }

    public double getVolume() {
        return volume;
    }

    public double getK() {
        return k;
    }

    public double getA() {
        return a;
    }

    public List<Double> getBoundarySurfacaceAreas() {
        return boundarySurfacaceAreas;
    }

    public List<Double> getAbsorptionCoefficients() {
        return absorptionCoefficients;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public void setK(double k) {
        this.k = k;
    }

    public void setA(double a) {
        this.a = a;
    }

    public void setT(double t) {
        this.t = t;
    }

    public void setBoundarySurfacaceAreas(List<Double> boundarySurfacaceAreas) {
        this.boundarySurfacaceAreas = boundarySurfacaceAreas;
    }

    public void setAbsorptionCoefficients(List<Double> absorptionCoefficients) {
        this.absorptionCoefficients = absorptionCoefficients;
    }

    @Override
    public String toString() {
        return "Sabine{" +
                "volume=" + volume +
                ", k=" + k +
                ", a=" + a +
                ", t=" + t +
                ", boundarySurfacaceAreas=" + boundarySurfacaceAreas +
                ", absorptionCoefficients=" + absorptionCoefficients +
                '}';
    }

    public Double calculateReverbTime(){
        List<Double> vals = IntStream.range(0, boundarySurfacaceAreas.size())
                .mapToObj(i -> boundarySurfacaceAreas.get(i) * absorptionCoefficients.get(i)).
                collect(Collectors.toList());                                 //multiply both lists elementwise
        this.a = vals.stream().reduce((Double)0.0, Double::sum)/vals.size();  //sum all elements and divide by count
        return this.t = this.k * this.volume / this.a;
    }
}
