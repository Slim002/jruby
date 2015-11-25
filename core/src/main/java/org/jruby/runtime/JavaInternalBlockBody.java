/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jruby.runtime;

import org.jruby.Ruby;
import org.jruby.parser.StaticScope;
import org.jruby.runtime.Block.Type;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * Represents a special Java implementation of a block.
 */
public abstract class JavaInternalBlockBody extends BlockBody {
    private final ThreadContext originalContext;
    private final String methodName;
    private final StaticScope dummyScope;

    /**
     * For blocks which can be executed in any thread concurrently.
     */
    public JavaInternalBlockBody(Ruby runtime, Signature signature) {
        this(runtime, null, null, signature);
    }

    /**
     * For blocks which cannot be executed in parallel.
     */
    public JavaInternalBlockBody(Ruby runtime, ThreadContext originalContext, String methodName, Signature signature) {
        super(signature);
        
        this.originalContext = originalContext;
        this.methodName = methodName;
        this.dummyScope = runtime.getStaticScopeFactory().getDummyScope();
    }
    
    // Make sure we are still on the same thread as originator if we care
    private void threadCheck(ThreadContext yieldingContext) {
        if (originalContext != null && yieldingContext != originalContext) {
            throw yieldingContext.runtime.newThreadError(methodName + " cannot be parallelized");
        }
    }

    @Override
    public IRubyObject call(ThreadContext context, IRubyObject[] args, Block b, Block.Type type) {
        return yield(context, args, null, b, type);
    }

    @Override
    public IRubyObject call(ThreadContext context, IRubyObject[] args, Block b,
                            Block.Type type, Block block) {
        return yield(context, args, null, b, type, block);
    }

    @Override
    protected IRubyObject doYield(ThreadContext context, IRubyObject value, Block b, Type type) {
        threadCheck(context);
        
        return yield(context, new IRubyObject[] { value });
    }

    @Override
    protected IRubyObject doYield(ThreadContext context, IRubyObject[] args, IRubyObject self, Block b, Type type) {
        threadCheck(context);
        
        return yield(context, args);
    }
    
    public abstract IRubyObject yield(ThreadContext context, IRubyObject[] args);

    @Override
    public StaticScope getStaticScope() {
        return dummyScope;
    }

    @Override
    public void setStaticScope(StaticScope newScope) {
    }

    @Override
    public String getFile() {
        return "(internal)";
    }

    @Override
    public int getLine() {
        return -1;
    }
    
}
