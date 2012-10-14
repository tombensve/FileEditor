package se.natusoft.tools.fileeditor.internal;

import se.natusoft.tools.codelicmgr.annotations.*;
import se.natusoft.tools.codelicmgr.enums.Source;

/**
 * This represents a positional that ranges from 1 to Integer.MAX_VALUE.
 */
@Project(
    name="FileEditor",
    codeVersion="2.0",
    description="Provides a programmable only text file editor. No gui is available. It supplies" +
                "most of the features you expect from an editor including selections."
)
@Copyright(year="2009", holder="Natusoft AB", rights="All rights reserved.")
@License(
    type="Apache",
    version="2.0",
    description="Apache Software License",
    source=Source.OPEN,
    text={
        "Licensed under the Apache License, Version 2.0 (the 'License');",
        "you may not use this file except in compliance with the License.",
        "You may obtain a copy of the License at",
        "",
        "  http://www.apache.org/licenses/LICENSE-2.0",
        "",
        "Unless required by applicable law or agreed to in writing, software",
        "distributed under the License is distributed on an 'AS IS' BASIS,",
        "WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.",
        "See the License for the specific language governing permissions and",
        "limitations under the License."
    }
)
@Author(
    name="Tommy Svensson",
    changes={
        @Change(when="2009-11-13", description="Created")
    }
)
public class TextPos {
    //
    // Constants
    //

    /** Can be used for the 2 arg constructor to make it more readable. */
    public static final boolean BASE_ZERO = true;

    //
    // Private Members
    //

    /** Value to pass to adjustLimits() if not max value is available. */
    public static final int NO_MAX_LIMIT = -1;

    /** The value that represents the last position. */
    private static final int LAST_VALUE = Integer.MAX_VALUE;

    /** The first TextPos value. */
    public static final TextPos FIRST = new TextPos(1);

    /** The second TextPos value. */
    public static final TextPos SECOND = new TextPos(2);

    /** Represents the unknown last position. */
    public static final TextPos LAST = new TextPos(LAST_VALUE);

    /** The current value. */
    private int value;
    
    /** Set to true if an invalid value is received. It will be adjusted to an ok value, but this flag will be set. */
    private boolean invalid = false;
        
    //
    // Constructors
    //
    
    /**
     * Creates a new instance of TextPos with the lowest possible position.
     */
    public TextPos() {
        this.value = 1;
    }

    /**
     * Creates a new instance of TextPos
     *
     * @param value The initial position (where 1 is the lowest value!).
     */
    public TextPos(int value) {
        this.value = value;
        adjustLimits(NO_MAX_LIMIT);
    }
    
    /**
     * Creates a new instance of TextPos
     *
     * @param baseZero If true the value is zero based.
     * @param value The initial position (lowest value is 0 or 1 depending on value of baseZero).
     */
    public TextPos(boolean baseZero, int value) {
        if (baseZero) {
            this.value = value + 1;
        }
        else {
            this.value = value;
        }
        adjustLimits(NO_MAX_LIMIT);
    }
    
    //
    // Methods
    //

    /**
     * Adjusts the limits of the value.
     */
    public final void adjustLimits(int max) {
        this.invalid = false;
        if (this.value < 1) {
            this.value = 1;
            this.invalid = true;
        }
        if (max > 0) {
            if (this.value > max) {
                this.value = max;
                this.invalid = true;
            }
        }
    }

    /**
     * Increments the position.
     *
     * @param size The amount to inrement with.
     */
    public TextPos increment(int size) {
        return new TextPos(this.value + size);
    }

    /**
     * Increments the position.
     */
    public TextPos increment() {
        return increment(1);
    }

    /**
     * Decrements the position.
     * 
     * @param size The amount to decrement the position with.
     */
    public TextPos decrement(int size) {
        return new TextPos(this.value - size);
    }

    /**
     * Decrements the position.
     */
    public TextPos decrement() {
        return decrement(1);
    }

    /**
     * Returns the value of this TextPos.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the value as a base 0 value.
     */
    public int baseZeroValue() {
        return this.value - 1;
    }

    /**
     * Returns true if the value was invalid and had to be corrected.
     */
    public boolean wasInvalid() {
        return this.invalid;
    }

    /**
     * Convenience for checking for valid instead of invalid.
     */
    public boolean wasValid() {
        return !wasInvalid();
    }

    /**
     * Returns true if this TextPos represents the last value.
     */
    public boolean isLastPosition() {
        return this.value == LAST_VALUE;
    }

    /**
     * Returns true if the specified object is equal to this object.
     * 
     * @param epos The object to compare to.
     */
    @Override
    public boolean equals(Object epos) {
        if (epos instanceof TextPos) {
            return ((TextPos)epos).value == this.value;
        }
        else if (epos instanceof Integer) {
            return ((Integer)epos).intValue() == this.value;
        }
        else {
            return false;
        }
    }

    /**
     * Returns true if this TextPos is less than the specified.
     * 
     * @param ltPos The TextPos to compare to.
     */
    public boolean lessThan(TextPos ltPos) {
        if (isLastPosition()) {
            return false;
        }
        return this.value < ltPos.value;
    }

    /**
     * Returns true if this TextPos is less than the specified.
     *
     * @param ltPos The TextPos to compare to.
     */
    public boolean lessThanOrEqual(TextPos ltPos) {
        if (isLastPosition()) {
            return false;
        }
        return this.value <= ltPos.value;
    }

    /**
     * Returns true if this TextPos is less than the specified value.
     *
     * @param ltPos The value to compare with.
     */
    public boolean lessThan(int ltPos) {
        if (isLastPosition()) {
            return false;
        }
        return this.value < ltPos;
    }

    /**
     * Returns true if this TextPos is less than the specified value.
     *
     * @param ltPos The value to compare with.
     */
    public boolean lessThanOrEqual(int ltPos) {
        if (isLastPosition()) {
            return false;
        }
        return this.value <= ltPos;
    }

    /**
     * Returns true if this TextPos is greater than the specified.
     *
     * @param gtPos
     */
    public boolean greaterThan(TextPos gtPos) {
        if (isLastPosition()) {
            return true;
        }
        return this.value > gtPos.value;
    }

    /**
     * Returns true if this TextPos is greater than the specified.
     *
     * @param gtPos
     */
    public boolean greaterThanOrEqual(TextPos gtPos) {
        if (isLastPosition()) {
            return true;
        }
        return this.value >= gtPos.value;
    }

    /**
     * Returns true if this TextPos is greater than the specified value.
     *
     * @param gtPos The value to comare with.
     */
    public boolean greaterThan(int gtPos) {
        if (isLastPosition()) {
            return true;
        }
        return this.value > gtPos;
    }

    /**
     * Returns true if this TextPos is greater than the specified value.
     *
     * @param gtPos The value to comare with.
     */
    public boolean greaterThanOrEqual(int gtPos) {
        if (isLastPosition()) {
            return true;
        }
        return this.value >= gtPos;
    }

    /**
     * Returns a new TextPos based on a base zero value.
     *
     * @param value The base zero value to convert to a TextPos.
     */
    public static TextPos fromBaseZeroValue(int value) {
        return new TextPos(TextPos.BASE_ZERO, value);
    }

    /**
     * Returns a TextPos based on a length value, i.e the last poition on a line.
     * <p>
     * This will correctly handle a length of 0.
     *
     * @param length The length value to make a TextPos of.
     */
    public static TextPos fromLengthValue(int length) {
        if (length <= 0) {
            length = 1;
        }
        return new TextPos(length);
    }

    /**
     * Returns a string representation of this TextPos.
     */
    @Override
    public String toString() {
        return "" + this.value;
    }

}
