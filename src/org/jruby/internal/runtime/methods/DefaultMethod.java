/***** BEGIN LICENSE BLOCK *****
 * Version: CPL 1.0/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Common Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.eclipse.org/legal/cpl-v10.html
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * rights and limitations under the License.
 *
 * Copyright (C) 2004-2008 Thomas E Enebo <enebo@acm.org>
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either of the GNU General Public License Version 2 or later (the "GPL"),
 * or the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the CPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the CPL, the GPL or the LGPL.
 ***** END LICENSE BLOCK *****/


////////////////////////////////////////////////////////////////////////////////
// NOTE: THIS FILE IS GENERATED! DO NOT EDIT THIS FILE!
// generated from: src/org/jruby/internal/runtime/methods/DefaultMethod.erb
// using arities: src/org/jruby/internal/runtime/methods/DefaultMethod.arities.erb
////////////////////////////////////////////////////////////////////////////////


package org.jruby.internal.runtime.methods;

import org.jruby.RubyModule;
import org.jruby.ast.ArgsNode;
import org.jruby.ast.Node;
import org.jruby.ast.executable.Script;
import org.jruby.internal.runtime.JumpTarget;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.parser.StaticScope;
import org.jruby.runtime.Arity;
import org.jruby.runtime.Block;
import org.jruby.runtime.ThreadContext;
import org.jruby.runtime.Visibility;
import org.jruby.runtime.builtin.IRubyObject;

/**
 * This is the mixed-mode method type.  It will call out to JIT compiler to see if the compiler
 * wants to JIT or not.  If the JIT compiler does JIT this it will return the new method
 * to be executed here instead of executing the interpreted version of this method.  The next
 * invocation of the method will end up causing the runtime to load and execute the newly JIT'd
 * method.
 *
 */
public class DefaultMethod extends DynamicMethod implements JumpTarget, MethodArgs {
    private DynamicMethod actualMethod;
    private final StaticScope staticScope;
    private final Node body;
    private final ArgsNode argsNode;
    private int callCount = 0;
    private final ISourcePosition position;
    
    public DefaultMethod(RubyModule implementationClass, StaticScope staticScope, Node body,
            ArgsNode argsNode, Visibility visibility, ISourcePosition position) {
        super(implementationClass, visibility, CallConfiguration.FrameFullScopeFull);
        actualMethod = new InterpretedMethod(implementationClass, staticScope, body, argsNode, visibility, position);
        this.argsNode = argsNode;
        this.body = body;
        this.staticScope = staticScope;
        this.position = position;

        assert argsNode != null;
    }

    public int getCallCount() {
        return callCount;
    }

    public int incrementCallCount() {
        return ++callCount;
    }

    public void setCallCount(int callCount) {
        this.callCount = callCount;
    }

    public Node getBodyNode() {
        return body;
    }

    public ArgsNode getArgsNode() {
        return argsNode;
    }

    public StaticScope getStaticScope() {
        return staticScope;
    }

    public void switchToJitted(Script jitCompiledScript, CallConfiguration jitCallConfig) {
        this.actualMethod = new JittedMethod(getImplementationClass(), staticScope, jitCompiledScript, jitCallConfig, getVisibility(), argsNode.getArity(), position);
        this.callCount = -1;
    }

    private DynamicMethod tryJitReturnMethod(ThreadContext context, String name) {
        context.getRuntime().getJITCompiler().tryJIT(this, context, name);
        return actualMethod;
    }

    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject[] args, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, args, block);
        }
        
        return actualMethod.call(context, self, clazz, name, args, block);
    }

    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject[] args) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, args);
        }

        return actualMethod.call(context, self, clazz, name, args);
    }

    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name);
        }

        return actualMethod.call(context, self, clazz, name );
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, block);
        }

        return actualMethod.call(context, self, clazz, name, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0);
        }

        return actualMethod.call(context, self, clazz, name , arg0);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4, arg5);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4, arg5, arg6);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7, IRubyObject arg8) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7, IRubyObject arg8, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, block);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7, IRubyObject arg8, IRubyObject arg9) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
        }

        return actualMethod.call(context, self, clazz, name , arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
    }
    @Override
    public IRubyObject call(ThreadContext context, IRubyObject self, RubyModule clazz, String name, IRubyObject arg0, IRubyObject arg1, IRubyObject arg2, IRubyObject arg3, IRubyObject arg4, IRubyObject arg5, IRubyObject arg6, IRubyObject arg7, IRubyObject arg8, IRubyObject arg9, Block block) {
        if (callCount >= 0) {
            return tryJitReturnMethod(context, name).call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, block);
        }

        return actualMethod.call(context, self, clazz, name, arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, block);
    }


    public ISourcePosition getPosition() {
        return position;
    }

    @Override
    public Arity getArity() {
        return argsNode.getArity();
    }

    public DynamicMethod dup() {
        return new DefaultMethod(getImplementationClass(), staticScope, body, argsNode, getVisibility(), position);
    }


}
