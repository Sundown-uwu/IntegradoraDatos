package com.example.demo.service.integradora.model;

public class HistoryAction {
    public enum ActionType { CREATE_LOAN, ADD_TO_WAITLIST, RETURN, AUTO_CREATE_LOAN }

    public ActionType type;
    public Integer userId;
    public Integer bookId;
    public Integer loanId;

    public HistoryAction() {}

    public HistoryAction(ActionType type, Integer userId, Integer bookId) {
        this(type, userId, bookId, null);
    }

    public HistoryAction(ActionType type, Integer userId, Integer bookId, Integer loanId) {
        this.type = type;
        this.userId = userId;
        this.bookId = bookId;
        this.loanId = loanId;
    }
}
