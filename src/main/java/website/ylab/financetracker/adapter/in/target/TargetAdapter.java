package website.ylab.financetracker.adapter.in.target;

import website.ylab.financetracker.application.domain.model.target.TargetModel;
import website.ylab.financetracker.application.port.in.target.TargetUseCase;

public class TargetAdapter {
    private final TargetUseCase useCase;
    private final TargetInterface cli;

    public TargetAdapter(TargetUseCase useCase, TargetInterface cli) {
        this.useCase = useCase;
        this.cli = cli;
    }

    public String create() {
        TargetModel target = new TargetModel();
        target.setTargetAmount(cli.getTargetAmount());
        return useCase.create(target).toString();
    }

    public String get() {
        return useCase.get().toString();
    }

    public String isReached() {
        return useCase.isReached() ? "Цель достигнута" : "Цель не достигнута";
    }
}