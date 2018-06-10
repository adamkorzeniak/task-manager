package com.adamkorzeniak.taskmanager.task.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;

import com.adamkorzeniak.taskmanager.task.model.Task;
import com.adamkorzeniak.taskmanager.task.model.TaskSearch;
import com.adamkorzeniak.taskmanager.user.model.User;
import com.adamkorzeniak.taskmanager.user.service.UserService;

public class TaskRepositoryImpl implements TaskRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;
	
	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private UserService userService;
	
	@Override
	public List<Task> search(TaskSearch task) {
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Task> query = cb.createQuery(Task.class);
		Root<Task> root = query.from(Task.class);
		Predicate criteria = cb.conjunction();
		
		if (task.getName() != null) {
			ParameterExpression<String> name = cb.parameter(String.class, NAME);
			criteria = cb.and(criteria, cb.like(root.get(NAME), name ));
		}
		if (task.getDescription() != null) {
			ParameterExpression<String> description = cb.parameter(String.class, DESCRIPTION);
			criteria = cb.and(criteria, cb.like(root.get(DESCRIPTION), description ));
		}
		if (task.getEstimationLessThan() != null) {
			ParameterExpression<Long> estimationLessThan = cb.parameter(Long.class, ESTIMATION_LESS_THAN);
			criteria = cb.and(criteria, cb.lt(root.get(ESTIMATION), estimationLessThan ));
		}
		if (task.getEstimationGreaterThan() != null) {
			ParameterExpression<Long> estimationGreaterThan = cb.parameter(Long.class, ESTIMATION_GREATER_THAN);
			criteria = cb.and(criteria, cb.gt(root.get(ESTIMATION), estimationGreaterThan ));
		}
		if (task.getTimeInvestedLessThan() != null) {
			ParameterExpression<Long> timeInvestedLessThan = cb.parameter(Long.class, TIME_INVESTED_LESS_THAN);
			criteria = cb.and(criteria, cb.lt(root.get(TIME_INVESTED), timeInvestedLessThan ));
		}
		if (task.getTimeInvestedGreaterThan() != null) {
			ParameterExpression<Long> timeInvestedGreaterThan = cb.parameter(Long.class, TIME_INVESTED_GREATER_THAN);
			criteria = cb.and(criteria, cb.gt(root.get(TIME_INVESTED), timeInvestedGreaterThan ));
		}
		if (task.getDateLessThan() != null) {
			ParameterExpression<LocalDateTime> dateLessThan = cb.parameter(LocalDateTime.class, DATE_LESS_THAN);
			criteria = cb.and(criteria, cb.lessThan(root.get(DATE), dateLessThan ));
		}
		if (task.getDateGreaterThan() != null) {
			ParameterExpression<LocalDateTime> dateGreaterThan = cb.parameter(LocalDateTime.class, DATE_GREATER_THAN);
			criteria = cb.and(criteria, cb.greaterThan(root.get(DATE), dateGreaterThan ));
		}
		if (task.getDeadlineLessThan() != null) {
			ParameterExpression<LocalDateTime> deadlineLessThan = cb.parameter(LocalDateTime.class, DEADLINE_LESS_THAN);
			criteria = cb.and(criteria, cb.lessThan(root.get(DEADLINE), deadlineLessThan ));
		}
		if (task.getDeadlineGreaterThan() != null) {
			ParameterExpression<LocalDateTime> deadlineGreaterThan = cb.parameter(LocalDateTime.class, DEADLINE_GREATER_THAN);
			criteria = cb.and(criteria, cb.greaterThan(root.get(DEADLINE), deadlineGreaterThan ));
		}
		if (task.getStatus() != null) {
			ParameterExpression<String> status = cb.parameter(String.class, STATUS);
			criteria = cb.and(criteria, cb.equal(root.get(STATUS), status ));
		}
		if (task.getParentTaskId() != null && task.getParentTaskId() > 0) {
			ParameterExpression<Task> parentTask = cb.parameter(Task.class, PARENT_TASK);
			criteria = cb.and(criteria, cb.equal(root.get(PARENT_TASK), parentTask ));
		}
		if (task.getParentTaskId() != null && task.getParentTaskId() == 0) {
			criteria = cb.and(criteria, cb.isNull(root.get(PARENT_TASK)));
		}
		
		query.select(root).where(criteria);
		TypedQuery<Task> tq = em.createQuery(query);
		
		if (task.getName() != null) {
			tq.setParameter(NAME, task.getName());
		}
		if (task.getDescription() != null) {
			tq.setParameter(DESCRIPTION, task.getDescription());
		}	
		if (task.getEstimationLessThan() != null) {
			tq.setParameter(ESTIMATION_LESS_THAN, task.getEstimationLessThan());
		}	
		if (task.getEstimationGreaterThan() != null) {
			tq.setParameter(ESTIMATION_GREATER_THAN, task.getEstimationGreaterThan());
		}	
		if (task.getTimeInvestedLessThan() != null) {
			tq.setParameter(TIME_INVESTED_LESS_THAN, task.getTimeInvestedLessThan());
		}	
		if (task.getTimeInvestedGreaterThan() != null) {
			tq.setParameter(TIME_INVESTED_GREATER_THAN, task.getTimeInvestedGreaterThan());
		}	
		if (task.getDateLessThan() != null) {
			tq.setParameter(DATE_LESS_THAN, task.getDateLessThan());
		}	
		if (task.getDateGreaterThan() != null) {
			tq.setParameter(DATE_GREATER_THAN, task.getDateGreaterThan());
		}	
		if (task.getDeadlineLessThan() != null) {
			tq.setParameter(DEADLINE_LESS_THAN, task.getDeadlineLessThan());
		}	
		if (task.getDeadlineGreaterThan() != null) {
			tq.setParameter(DEADLINE_GREATER_THAN, task.getDeadlineGreaterThan());
		}	
		if (task.getStatus() != null) {
			tq.setParameter(STATUS, task.getStatus());
		}	
		if (task.getParentTaskId() != null && task.getParentTaskId() > 0) {
			User user = userService.getUser(userService.getPrincipal());
			Task parent = taskRepository.findByUserAndId(user, task.getParentTaskId());
			tq.setParameter(PARENT_TASK, parent);
		}	
		
		List<Task> results = tq.getResultList();
		return results;
	}
	

	private final String ESTIMATION_LESS_THAN = "estimationLessThan";
	private final String ESTIMATION_GREATER_THAN = "estimationGreaterThan";
	private final String TIME_INVESTED_LESS_THAN = "timeInvestedLessThan";
	private final String TIME_INVESTED_GREATER_THAN = "timeInvestedGreaterThan";
	private final String DATE_LESS_THAN = "dateLessThan";
	private final String DATE_GREATER_THAN = "dateGreaterThan";
	private final String DEADLINE_LESS_THAN = "deadlineLessThan";
	private final String DEADLINE_GREATER_THAN = "deadlineGreaterThan";
	
	private final String NAME = "name";
	private final String DESCRIPTION = "description";
	private final String ESTIMATION = "minutesEstimation";
	private final String TIME_INVESTED = "minutesInvested";
	private final String DATE = "date";
	private final String DEADLINE = "deadline";
	private final String STATUS = "status";
	private final String PARENT_TASK = "parentTask";
}
