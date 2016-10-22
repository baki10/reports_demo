package com.bakigoal.report.birt.dao;

import com.bakigoal.report.birt.base.dao.ReportDao;
import com.bakigoal.report.birt.dto.CarDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class CarReportDao extends ReportDao<CarDto> {
  @Override
  public List<Object[]> getList(CarDto dto) {
    List<Object[]> list = new ArrayList<>();

    List<CarDto> allCars = getAllCars();
    for (CarDto car : allCars) {
      Object[] objects = new Object[3];
      objects[0] = car.getMake();   //make
      objects[1] = car.getModel();  //model
      objects[2] = car.getYear();   //year
      list.add(objects);
    }
    return list;
  }

  private List<CarDto> getAllCars() {
    CarDto car1 = new CarDto();
    car1.setYear("2000");
    car1.setMake("Chevrolet");
    car1.setModel("Corvette");
    CarDto car2 = new CarDto();
    car2.setYear("2005");
    car2.setMake("Dodge");
    car2.setModel("Viper");
    CarDto car3 = new CarDto();
    car3.setYear("2002");
    car3.setMake("Ford");
    car3.setModel("Mustang GT");
    return Arrays.asList(car1, car2, car3);


  }
}
