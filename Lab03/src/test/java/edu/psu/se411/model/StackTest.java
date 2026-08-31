package edu.psu.se411.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Stack}.
 */
public class StackTest {

    // ----- Required exercise 3.c: push -> push -> pop returns the latest pushed element -----
    @Test
    public void push_thenPush_thenPop_returnsLastPushedElement() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("Z");
        stringStack.push("A");
        assertEquals("A", stringStack.pop());
    }

    // ----- Required exercise 3.f.i: popping an empty stack raises an exception -----
    @Test
    public void pop_emptyStack_throwsNoSuchElementException() {
        Stack<String> stringStack = new Stack<>();

        NoSuchElementException thrown = assertThrows(
                NoSuchElementException.class,
                () -> stringStack.pop(),
                "Expected pop from empty Stack to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().equals("Stack is empty, cannot pop"));
    }

    // ----- Required exercise 3.f.ii: pushed elements are popped in reverse (LIFO) order -----
    @Test
    public void push_multipleElements_popReturnsReverseOrder() {
        Stack<Integer> intStack = new Stack<>();
        intStack.push(1);
        intStack.push(2);
        intStack.push(3);

        assertEquals(3, intStack.pop());
        assertEquals(2, intStack.pop());
        assertEquals(1, intStack.pop());
    }

    // ----- Additional edge-case coverage (as requested via the AI-copilot step) -----

    @Test
    public void pop_afterEmptyingStack_throwsAgain() {
        // State changes across multiple calls: stack should behave the same way
        // once emptied a second time, not just on first construction.
        Stack<String> stringStack = new Stack<>();
        stringStack.push("only");
        stringStack.pop();

        assertThrows(NoSuchElementException.class, stringStack::pop);
    }

    @Test
    public void push_nullElement_isStoredAndPoppedAsNull() {
        // E is unbounded, so null is a legal value to push.
        Stack<String> stringStack = new Stack<>();
        stringStack.push(null);
        assertNull(stringStack.pop());
    }

    @Test
    public void constructor_defaultCapacity_behavesLikeAnyOtherStack() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("A");
        assertEquals("A", stringStack.pop());
    }

    @Test
    public void constructor_explicitCapacity_behavesLikeAnyOtherStack() {
        Stack<String> stringStack = new Stack<>(2);
        stringStack.push("A");
        stringStack.push("B");
        assertEquals("B", stringStack.pop());
        assertEquals("A", stringStack.pop());
    }

    @Test
    public void constructor_nonPositiveCapacity_fallsBackToDefaultAndStillWorks() {
        // capacity <= 0 falls back to the default capacity (10) internally;
        // this should not affect observable push/pop behavior.
        Stack<String> stringStack = new Stack<>(0);
        stringStack.push("A");
        assertEquals("A", stringStack.pop());
    }

    @Test
    public void push_beyondInitialCapacity_growsAndStillPopsInOrder() {
        // Initial capacity is small; pushing more elements than that must not
        // lose data or break ordering (relies on ArrayList's automatic growth).
        Stack<Integer> intStack = new Stack<>(2);
        for (int i = 0; i < 20; i++) {
            intStack.push(i);
        }
        for (int i = 19; i >= 0; i--) {
            assertEquals(i, intStack.pop());
        }
        assertThrows(NoSuchElementException.class, intStack::pop);
    }

    /*
     * Coverage notes:
     * - Stack has exactly two public methods: push(E) and pop(). Both are
     *   exercised for normal behavior, boundary behavior (empty stack),
     *   and state changes across multiple calls.
     * - There are no branches other than the empty-check in pop(); both
     *   branches (empty / non-empty) are covered.
     * - Both public constructors (default and capacity-based) are covered,
     *   including the capacity<=0 fallback branch.
     * - No private/internal methods exist to test directly; everything is
     *   exercised through the public push/pop API only.
     */
}