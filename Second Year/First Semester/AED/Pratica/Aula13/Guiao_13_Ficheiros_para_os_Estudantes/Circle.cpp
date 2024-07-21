//
// Algoritmos e Estruturas de Dados - 2023/2024
//
// COMPLETE the code, according to Circle.h
//

#include "Circle.h"

#define _USE_MATH_DEFINES

#include <cassert>
#include <cmath>
#include <iostream>
#include <string>

#include "Figure.h"
#include "Point.h"

Circle::Circle(void) : Figure(), radius_(1.0) {
  // Circle of radius 1 and centered at (0,0)
  
}

Circle::Circle(Point center, const std::string& color, double length) : Figure(center, color), radius_(length) {
  // Ensure that the radius is larger than 0.0
  assert(length > 0.0);
}

Circle::Circle(double x, double y, const std::string& color, double length) : Figure(x, y, color), radius_(length) {
  // Ensure that the radius is larger than 0.0
  assert(length > 0.0);
}

double Circle::GetRadius(void) const { 
  return radius_; 
}

void Circle::SetRadius(double length) {
  // Ensure that the radius is larger than 0.0
  radius_ = length;
  assert(length > 0.0);
}

std::string Circle::GetClassName(void) const { return "Circle"; }

double Circle::Area(void) const {
   return M_PI * radius_ * radius_;
}

double Circle::Perimeter(void) const {
  return 2 * M_PI * radius_;
}

bool Circle::Intersects(const Circle& c) const {
  // |r1 - r2| < dist(C1,C2) < r1 + r2

  double distance_between_centers = GetCenter().DistanceTo(c.GetCenter());

  double sum_of_radii = radius_ + c.radius_;

  double absolute_difference_of_radii = std::abs(radius_ - c.radius_);

  return ((absolute_difference_of_radii < distance_between_centers) &&
          (distance_between_centers < sum_of_radii));
}

std::ostream& operator<<(std::ostream& os, const Circle& obj) {
  os << "Center: " << obj.GetCenter() << std::endl;
  os << "Color: " << obj.GetColor() << std::endl;
  os << "Radius = " << obj.radius_ << std::endl;
  return os;
}

std::istream& operator>>(std::istream& is, Circle& obj) {
  std::cout << "Center: ";
  Point p;
  is >>  p;
  obj.SetCenter(p);

  std::cout << "Color: ";
  std::string s;
  is >> s;
  obj.SetColor(s);

  std::cout << "Radius = ";
  double r;
  is >> r;
  obj.SetRadius(r);

}
