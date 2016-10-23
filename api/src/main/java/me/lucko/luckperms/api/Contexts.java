/*
 * Copyright (c) 2016 Lucko (Luck) <luck@lucko.me>
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package me.lucko.luckperms.api;

import me.lucko.luckperms.api.context.ContextSet;

import java.util.Map;

/**
 * Represents the context and options for a permission lookup.
 * All values are immutable.
 * @since 2.11
 */
public class Contexts {
    public static final String SERVER_KEY = "server";
    public static final String WORLD_KEY = "world";

    /**
     * Gets a context that will allow all nodes
     * @return a context that will not apply any filters
     */
    public static Contexts allowAll() {
        return new Contexts(ContextSet.empty(), true, true, true, true, true, true);
    }

    public static Contexts of(ContextSet context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups, boolean op) {
        return new Contexts(context, includeGlobal, includeGlobalWorld, applyGroups, applyGlobalGroups, applyGlobalWorldGroups, op);
    }

    public Contexts(ContextSet context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups, boolean op) {
        if (context == null) {
            throw new NullPointerException("context");
        }

        this.context = context.makeImmutable();
        this.includeGlobal = includeGlobal;
        this.includeGlobalWorld = includeGlobalWorld;
        this.applyGroups = applyGroups;
        this.applyGlobalGroups = applyGlobalGroups;
        this.applyGlobalWorldGroups = applyGlobalWorldGroups;
        this.op = op;
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public static Contexts of(Map<String, String> context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups) {
        return new Contexts(context, includeGlobal, includeGlobalWorld, applyGroups, applyGlobalGroups, applyGlobalWorldGroups);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public static Contexts of(Map<String, String> context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups, boolean op) {
        return new Contexts(context, includeGlobal, includeGlobalWorld, applyGroups, applyGlobalGroups, applyGlobalWorldGroups, op);
    }

    @Deprecated
    public Contexts(Map<String, String> context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups, boolean op) {
        this(context == null ? null : ContextSet.fromMap(context), includeGlobal, includeGlobalWorld, applyGroups, applyGlobalGroups, applyGlobalWorldGroups, op);
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public Contexts(Map<String, String> context, boolean includeGlobal, boolean includeGlobalWorld, boolean applyGroups, boolean applyGlobalGroups, boolean applyGlobalWorldGroups) {
        this(context, includeGlobal, includeGlobalWorld, applyGroups, applyGlobalGroups, applyGlobalWorldGroups, false);
    }

    /**
     * The contexts that apply for this lookup
     * The keys for servers and worlds are defined as static values.
     */
    private final ContextSet context;

    /**
     * The mode to parse defaults on Bukkit
     * @since 2.12
     */
    private final boolean op;

    /**
     * If global or non server specific nodes should be applied
     */
    private final boolean includeGlobal;

    /**
     * If global or non world specific nodes should be applied
     */
    private final boolean includeGlobalWorld;

    /**
     * If parent groups should be applied
     */
    private final boolean applyGroups;

    /**
     * If global or non server specific group memberships should be applied
     */
    private final boolean applyGlobalGroups;

    /**
     * If global or non world specific group memberships should be applied
     */
    private final boolean applyGlobalWorldGroups;

    /**
     * Gets the contexts that apply for this lookup
     * @return an immutable set of context key value pairs
     * @since 2.13
     */
    public ContextSet getContexts() {
        return this.context;
    }

    /**
     * Gets the contexts that apply for this lookup
     * @return an immutable map of context key value pairs
     * @deprecated in favour of {@link #getContexts()}
     */
    @Deprecated
    public Map<String, String> getContext() {
        return this.context.toMap();
    }

    /**
     * Gets if OP defaults should be included
     * @return true if op defaults should be included
     */
    public boolean isOp() {
        return this.op;
    }

    /**
     * Gets if global or non server specific nodes should be applied
     * @return true if global or non server specific nodes should be applied
     */
    public boolean isIncludeGlobal() {
        return this.includeGlobal;
    }

    /**
     * Gets if global or non world specific nodes should be applied
     * @return true if global or non world specific nodes should be applied
     */
    public boolean isIncludeGlobalWorld() {
        return this.includeGlobalWorld;
    }

    /**
     * Gets if parent groups should be applied
     * @return true if parent groups should be applied
     */
    public boolean isApplyGroups() {
        return this.applyGroups;
    }

    /**
     * Gets if global or non server specific group memberships should be applied
     * @return true if global or non server specific group memberships should be applied
     */
    public boolean isApplyGlobalGroups() {
        return this.applyGlobalGroups;
    }

    /**
     * Gets if global or non world specific group memberships should be applied
     * @return true if global or non world specific group memberships should be applied
     */
    public boolean isApplyGlobalWorldGroups() {
        return this.applyGlobalWorldGroups;
    }

    public String toString() {
        return "Contexts(" +
                "context=" + this.getContexts() + ", " +
                "op=" + this.isOp() + ", " +
                "includeGlobal=" + this.isIncludeGlobal() + ", " +
                "includeGlobalWorld=" + this.isIncludeGlobalWorld() + ", " +
                "applyGroups=" + this.isApplyGroups() + ", " +
                "applyGlobalGroups=" + this.isApplyGlobalGroups() + ", " +
                "applyGlobalWorldGroups=" + this.isApplyGlobalWorldGroups() +
                ")";
    }

    /*
     * Ugly auto-generated lombok code
     */

    /**
     * Check for equality
     * @param o the other
     * @return true if equal
     * @since 2.12
     */
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Contexts)) return false;
        final Contexts other = (Contexts) o;
        final Object this$context = this.getContexts();
        final Object other$context = other.getContexts();
        if (this$context == null ? other$context != null : !this$context.equals(other$context)) return false;
        if (this.isOp() != other.isOp()) return false;
        if (this.isIncludeGlobal() != other.isIncludeGlobal()) return false;
        if (this.isIncludeGlobalWorld() != other.isIncludeGlobalWorld()) return false;
        if (this.isApplyGroups() != other.isApplyGroups()) return false;
        if (this.isApplyGlobalGroups() != other.isApplyGlobalGroups()) return false;
        if (this.isApplyGlobalWorldGroups() != other.isApplyGlobalWorldGroups()) return false;
        return true;
    }

    /**
     * Gets the hashcode
     * @return the hashcode
     * @since 2.12
     */
    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $context = this.getContexts();
        result = result * PRIME + ($context == null ? 43 : $context.hashCode());
        result = result * PRIME + (this.isOp() ? 79 : 97);
        result = result * PRIME + (this.isIncludeGlobal() ? 79 : 97);
        result = result * PRIME + (this.isIncludeGlobalWorld() ? 79 : 97);
        result = result * PRIME + (this.isApplyGroups() ? 79 : 97);
        result = result * PRIME + (this.isApplyGlobalGroups() ? 79 : 97);
        result = result * PRIME + (this.isApplyGlobalWorldGroups() ? 79 : 97);
        return result;
    }

}
