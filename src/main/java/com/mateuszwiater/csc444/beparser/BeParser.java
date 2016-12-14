package com.mateuszwiater.csc444.beparser;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.mateuszwiater.csc444.beparser.GrammarLexer.*;

public class BeParser extends GrammarBaseListener {
    private char id;
    private final Stack<Boolean> stack;
    private final Map<Character, Boolean> variables;

    /**
     * Main method.
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        new BeParser();
    }

    /**
     * The Constructor.
     */
    private BeParser() {
        // Create the stack
        stack     = new Stack<>();
        // Set up the variables
        variables = IntStream.range(97, 123)
                .mapToObj(i -> (char)i).collect(Collectors.toMap(Function.identity(), c -> false));

        // Accept user input
        System.out.println("Input Boolean Expression Below:");
        final Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while(!input.equals("quit")) {
            input = parse(input);
            if(!input.isEmpty()) {
                System.out.println(input);
            }
            input = scanner.nextLine();
        }
    }

    /**
     * Takes a boolean expression string and evaluates it based on the Grammar.
     *
     * @param input a boolean expression string.
     * @return an empty string if assignment, 0 or 1 if query.
     */
    private String parse(final String input) {
        GrammarParser parser = new GrammarParser(new CommonTokenStream(new GrammarLexer(new ANTLRInputStream(input))));
        parser.addParseListener(this);
        parser.goal();
        if(parser.getNumberOfSyntaxErrors() != 0) {
            return "";
        } else {
            return String.valueOf(stack.isEmpty() ? "" : stack.pop() ? 1 : 0);
        }
    }

    /**
     * Sets the current id.
     *
     * @param ctx parser context.
     */
    @Override
    public void enterGoal(GrammarParser.GoalContext ctx) {
        id = ctx.getStart().getText().charAt(0);
    }

    /**
     * Takes care of if the expression was an assignment or a query.
     *
     * @param ctx parser context.
     */
    @Override
    public void exitQ(GrammarParser.QContext ctx) {
        switch(ctx.getStart().getType()) {
            case QMARK:
                stack.push(variables.get(id));
                break;
            case EQ:
                if(!stack.isEmpty()) {
                    variables.put(id, stack.pop());
                }
                break;
        }
    }

    /**
     * Takes care of the XOR logic.
     *
     * @param ctx parser context.
     */
    @Override
    public void exitEp(GrammarParser.EpContext ctx) {
        switch(ctx.getStart().getType()) {
            case XOR:
                final boolean a = stack.pop(), b = stack.pop();
                stack.push(a ^ b);
                break;
        }
    }

    /**
     * Takes care of the OR logic.
     *
     * @param ctx parser context.
     */
    @Override
    public void exitGp(GrammarParser.GpContext ctx) {
        switch(ctx.getStart().getType()) {
            case OR:
                final boolean a = stack.pop(), b = stack.pop();
                stack.push(a || b);
                break;
        }
    }

    /**
     * Takes care of the AND logic.
     *
     * @param ctx parser context.
     */
    @Override
    public void exitHp(GrammarParser.HpContext ctx) {
        switch(ctx.getStart().getType()) {
            case AND:
                final boolean a = stack.pop(), b = stack.pop();
                stack.push(a && b);
                break;
        }
    }

    /**
     * Takes care of the terminal logic.
     *
     * @param ctx parser context.
     */
    @Override
    public void exitT(GrammarParser.TContext ctx) {
        switch(ctx.getStart().getType()) {
            case TRUE:
                stack.push(true);
                break;
            case FALSE:
                stack.push(false);
                break;
            case ID:
                stack.push(variables.get(ctx.getStart().getText().charAt(0)));
                break;
            case NOT:
                stack.push(!stack.pop());
                break;
        }
    }
}
