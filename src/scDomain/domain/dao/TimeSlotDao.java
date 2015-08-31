/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scDomain.domain.dao;

import java.util.ArrayList;
import scDomain.domain.objects.*;

/**
 *
 * @author Morgan
 */
public interface TimeSlotDao extends DomainDao.Updater<TimeSlot, TimeSlot.Key, TimeSlot.Builder> {
    public ArrayList<TimeSlot> findByAgent(Agent.Key agent);
    public ArrayList<TimeSlot> findByDepartment(Department.Key department);
    
    public interface CategoryDao extends DomainDao.FindAll<TimeSlot.Category, TimeSlot.Category.Key> {}
    public interface TypeDao extends DomainDao.FindAll<TimeSlot.Type, TimeSlot.Type.Key> {}
}