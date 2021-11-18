package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.CurvePoint;

public interface ICurvePointService {
    
    public List<CurvePoint> getAllCurvePoint();
    
    public boolean addCurvePoint(CurvePoint newCurvePoint);
    
    public boolean updateCurvePoint(int id, CurvePoint curvePoint);
    
    public boolean deleteCurvePoint(int id);
    
    public CurvePoint getCurvePointById(int id);

}
