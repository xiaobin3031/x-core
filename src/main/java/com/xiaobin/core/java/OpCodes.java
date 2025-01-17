package com.xiaobin.core.java;

import lombok.Getter;

/**
 * created by xuweibin at 2025-01-14T10:06:10.450581300
 */
@Getter
public abstract class OpCodes {

    final int opCode;
    final String name;

    public OpCodes(int opCode, String name) {
        this.opCode = opCode;
        this.name = name;
    }

    static OpCodes fromOpCode(int opCode) {
        return switch (opCode) {
            case 50 -> new Aaload();
            case 83 -> new Aastore();
            case 1 -> new Aconst_null();
            case 25 -> new Aload();
            case 42 -> new Aload_0();
            case 43 -> new Aload_1();
            case 44 -> new Aload_2();
            case 45 -> new Aload_3();
            case 189 -> new Anewarray();
            case 176 -> new Areturn();
            case 190 -> new Arraylength();
            case 58 -> new Astore();
            case 75 -> new Astore_0();
            case 76 -> new Astore_1();
            case 77 -> new Astore_2();
            case 78 -> new Astore_3();
            case 191 -> new Athrow();
            case 51 -> new Baload();
            case 84 -> new Bastore();
            case 16 -> new Bipush();
            case 52 -> new Caload();
            case 85 -> new Castore();
            case 192 -> new Checkcast();
            case 144 -> new D2f();
            case 142 -> new D2i();
            case 143 -> new D2l();
            case 99 -> new Dadd();
            case 49 -> new Dalod();
            case 82 -> new Dastore();
            case 152 -> new Dcmpg();
            case 151 -> new Dcmpl();
            case 14 -> new Dconst_0();
            case 15 -> new Dconst_1();
            case 111 -> new Ddiv();
            case 24 -> new Dload();
            case 38 -> new Dload_0();
            case 39 -> new Dload_1();
            case 40 -> new Dload_2();
            case 41 -> new Dload_3();
            case 107 -> new Dmul();
            case 119 -> new Dneg();
            case 115 -> new Drem();
            case 175 -> new Dreturn();
            case 57 -> new Dstore();
            case 71 -> new Dstore_0();
            case 72 -> new Dstore_1();
            case 73 -> new Dstore_2();
            case 74 -> new Dstore_3();
            case 103 -> new Dsub();
            case 89 -> new Dup();
            case 90 -> new Dup_x1();
            case 91 -> new Dup_x2();
            case 92 -> new Dup2();
            case 93 -> new Dup2_x1();
            case 94 -> new Dup2_x2();
            case 141 -> new F2d();
            case 139 -> new F2i();
            case 140 -> new F2l();
            case 98 -> new Fadd();
            case 48 -> new Faload();
            case 81 -> new Fastore();
            case 150 -> new Fcmpg();
            case 149 -> new Fcmpl();
            case 11 -> new Fconst_0();
            case 12 -> new Fconst_1();
            case 13 -> new Fconst_2();
            case 110 -> new Fdiv();
            case 23 -> new Fload();
            case 34 -> new Fload_0();
            case 35 -> new Fload_1();
            case 36 -> new Fload_2();
            case 37 -> new Fload_3();
            case 106 -> new Fmul();
            case 118 -> new Fneg();
            case 114 -> new Frem();
            case 174 -> new Freturn();
            case 56 -> new Fstore();
            case 67 -> new Fstore_0();
            case 68 -> new Fstore_1();
            case 69 -> new Fstore_2();
            case 70 -> new Fstore_3();
            case 102 -> new Fsub();
            case 180 -> new Getfield();
            case 178 -> new Getstatic();
            case 167 -> new Goto();
            case 200 -> new Goto_w();
            case 145 -> new I2b();
            case 146 -> new I2c();
            case 135 -> new I2d();
            case 134 -> new I2f();
            case 133 -> new I2l();
            case 147 -> new I2s();
            case 96 -> new Iadd();
            case 46 -> new Iaload();
            case 126 -> new Iand();
            case 79 -> new Iastore();
            case 2 -> new Iconst_m1();
            case 3 -> new Iconst_0();
            case 4 -> new Iconst_1();
            case 5 -> new Iconst_2();
            case 6 -> new Iconst_3();
            case 7 -> new Iconst_4();
            case 8 -> new Iconst_5();
            case 108 -> new Idiv();
            case 165 -> new If_acmpeq();
            case 166 -> new If_acmpne();
            case 159 -> new If_icmpeq();
            case 160 -> new If_icmpne();
            case 161 -> new If_icmplt();
            case 162 -> new If_icmpge();
            case 163 -> new If_icmpgt();
            case 164 -> new If_icmple();
            case 153 -> new Ifeq();
            case 154 -> new Ifne();
            case 155 -> new Iflt();
            case 156 -> new Ifge();
            case 157 -> new Ifgt();
            case 158 -> new Ifle();
            case 199 -> new Ifnonnull();
            case 198 -> new Ifnull();
            case 132 -> new Iinc();
            case 21 -> new Iload();
            case 26 -> new Iload_0();
            case 27 -> new Iload_1();
            case 28 -> new Iload_2();
            case 29 -> new Iload_3();
            case 104 -> new Imul();
            case 116 -> new Ineg();
            case 193 -> new Instanceof();
            case 186 -> new Invokedynamic();
            case 185 -> new Invokeinterface();
            case 183 -> new Invokespecial();
            case 184 -> new Invokestatic();
            case 182 -> new Invokevirtual();
            case 128 -> new Ior();
            case 112 -> new Irem();
            case 172 -> new Ireturn();
            case 120 -> new Ishl();
            case 122 -> new Ishr();
            case 54 -> new Istore();
            case 59 -> new Istore_0();
            case 60 -> new Istore_1();
            case 61 -> new Istore_2();
            case 62 -> new Istore_3();
            case 100 -> new Isub();
            case 124 -> new Iushr();
            case 130 -> new Ixor();
            case 168 -> new Jsr();
            case 201 -> new Jsr_w();
            case 138 -> new L2d();
            case 137 -> new L2f();
            case 136 -> new L2i();
            case 97 -> new Ladd();
            case 47 -> new Laload();
            case 127 -> new Land();
            case 80 -> new Lastore();
            case 148 -> new Lcmp();
            case 9 -> new Lconst_0();
            case 10 -> new Lconst_1();
            case 18 -> new Ldc();
            case 19 -> new Ldc_w();
            case 20 -> new Ldc2_w();
            case 109 -> new Ldiv();
            case 22 -> new Lload();
            case 30 -> new Lload_0();
            case 31 -> new Lload_1();
            case 32 -> new Lload_2();
            case 33 -> new Lload_3();
            case 105 -> new Lmul();
            case 117 -> new Lneg();
            case 171 -> new Lookupswitch();
            case 129 -> new Lor();
            case 113 -> new Lrem();
            case 173 -> new Lreturn();
            case 121 -> new Lshl();
            case 123 -> new Lshr();
            case 55 -> new Lstore();
            case 63 -> new Lstore_0();
            case 64 -> new Lstore_1();
            case 65 -> new Lstore_2();
            case 66 -> new Lstore_3();
            case 101 -> new Lsub();
            case 125 -> new Lushr();
            case 131 -> new Lxor();
            case 194 -> new Monitorenter();
            case 195 -> new Monitorexit();
            case 197 -> new Multianewarray();
            case 187 -> new New();
            case 188 -> new Newarray();
            case 0 -> new Nop();
            case 87 -> new Pop();
            case 88 -> new Pop2();
            case 181 -> new Putfield();
            case 179 -> new Putstatic();
            case 169 -> new Ret();
            case 177 -> new Return();
            case 53 -> new Saload();
            case 86 -> new Sastore();
            case 17 -> new Sipush();
            case 95 -> new Swap();
            case 170 -> new Tableswitch();
            case 196 -> new Wide();
            default -> null;
        };
    }

    public interface Visitor {
        void visitAaload(Aaload _aaload);

        void visitAastore(Aastore _aastore);

        void visitAconst_null(Aconst_null _aconst_null);

        void visitAload(Aload _aload);

        void visitAload_0(Aload_0 _aload_0);

        void visitAload_1(Aload_1 _aload_1);

        void visitAload_2(Aload_2 _aload_2);

        void visitAload_3(Aload_3 _aload_3);

        void visitAnewarray(Anewarray _anewarray);

        void visitAreturn(Areturn _areturn);

        void visitArraylength(Arraylength _arraylength);

        void visitAstore(Astore _astore);

        void visitAstore_0(Astore_0 _astore_0);

        void visitAstore_1(Astore_1 _astore_1);

        void visitAstore_2(Astore_2 _astore_2);

        void visitAstore_3(Astore_3 _astore_3);

        void visitAthrow(Athrow _athrow);

        void visitBaload(Baload _baload);

        void visitBastore(Bastore _bastore);

        void visitBipush(Bipush _bipush);

        void visitCaload(Caload _caload);

        void visitCastore(Castore _castore);

        void visitCheckcast(Checkcast _checkcast);

        void visitD2f(D2f _d2f);

        void visitD2i(D2i _d2i);

        void visitD2l(D2l _d2l);

        void visitDadd(Dadd _dadd);

        void visitDalod(Dalod _dalod);

        void visitDastore(Dastore _dastore);

        void visitDcmpg(Dcmpg _dcmpg);

        void visitDcmpl(Dcmpl _dcmpl);

        void visitDconst_0(Dconst_0 _dconst_0);

        void visitDconst_1(Dconst_1 _dconst_1);

        void visitDdiv(Ddiv _ddiv);

        void visitDload(Dload _dload);

        void visitDload_0(Dload_0 _dload_0);

        void visitDload_1(Dload_1 _dload_1);

        void visitDload_2(Dload_2 _dload_2);

        void visitDload_3(Dload_3 _dload_3);

        void visitDmul(Dmul _dmul);

        void visitDneg(Dneg _dneg);

        void visitDrem(Drem _drem);

        void visitDreturn(Dreturn _dreturn);

        void visitDstore(Dstore _dstore);

        void visitDstore_0(Dstore_0 _dstore_0);

        void visitDstore_1(Dstore_1 _dstore_1);

        void visitDstore_2(Dstore_2 _dstore_2);

        void visitDstore_3(Dstore_3 _dstore_3);

        void visitDsub(Dsub _dsub);

        void visitDup(Dup _dup);

        void visitDup_x1(Dup_x1 _dup_x1);

        void visitDup_x2(Dup_x2 _dup_x2);

        void visitDup2(Dup2 _dup2);

        void visitDup2_x1(Dup2_x1 _dup2_x1);

        void visitDup2_x2(Dup2_x2 _dup2_x2);

        void visitF2d(F2d _f2d);

        void visitF2i(F2i _f2i);

        void visitF2l(F2l _f2l);

        void visitFadd(Fadd _fadd);

        void visitFaload(Faload _faload);

        void visitFastore(Fastore _fastore);

        void visitFcmpg(Fcmpg _fcmpg);

        void visitFcmpl(Fcmpl _fcmpl);

        void visitFconst_0(Fconst_0 _fconst_0);

        void visitFconst_1(Fconst_1 _fconst_1);

        void visitFconst_2(Fconst_2 _fconst_2);

        void visitFdiv(Fdiv _fdiv);

        void visitFload(Fload _fload);

        void visitFload_0(Fload_0 _fload_0);

        void visitFload_1(Fload_1 _fload_1);

        void visitFload_2(Fload_2 _fload_2);

        void visitFload_3(Fload_3 _fload_3);

        void visitFmul(Fmul _fmul);

        void visitFneg(Fneg _fneg);

        void visitFrem(Frem _frem);

        void visitFreturn(Freturn _freturn);

        void visitFstore(Fstore _fstore);

        void visitFstore_0(Fstore_0 _fstore_0);

        void visitFstore_1(Fstore_1 _fstore_1);

        void visitFstore_2(Fstore_2 _fstore_2);

        void visitFstore_3(Fstore_3 _fstore_3);

        void visitFsub(Fsub _fsub);

        void visitGetfield(Getfield _getfield);

        void visitGetstatic(Getstatic _getstatic);

        void visitGoto(Goto _goto);

        void visitGoto_w(Goto_w _goto_w);

        void visitI2b(I2b _i2b);

        void visitI2c(I2c _i2c);

        void visitI2d(I2d _i2d);

        void visitI2f(I2f _i2f);

        void visitI2l(I2l _i2l);

        void visitI2s(I2s _i2s);

        void visitIadd(Iadd _iadd);

        void visitIaload(Iaload _iaload);

        void visitIand(Iand _iand);

        void visitIastore(Iastore _iastore);

        void visitIconst_m1(Iconst_m1 _iconst_m1);

        void visitIconst_0(Iconst_0 _iconst_0);

        void visitIconst_1(Iconst_1 _iconst_1);

        void visitIconst_2(Iconst_2 _iconst_2);

        void visitIconst_3(Iconst_3 _iconst_3);

        void visitIconst_4(Iconst_4 _iconst_4);

        void visitIconst_5(Iconst_5 _iconst_5);

        void visitIdiv(Idiv _idiv);

        void visitIf_acmpeq(If_acmpeq _if_acmpeq);

        void visitIf_acmpne(If_acmpne _if_acmpne);

        void visitIf_icmpeq(If_icmpeq _if_icmpeq);

        void visitIf_icmpne(If_icmpne _if_icmpne);

        void visitIf_icmplt(If_icmplt _if_icmplt);

        void visitIf_icmpge(If_icmpge _if_icmpge);

        void visitIf_icmpgt(If_icmpgt _if_icmpgt);

        void visitIf_icmple(If_icmple _if_icmple);

        void visitIfeq(Ifeq _ifeq);

        void visitIfne(Ifne _ifne);

        void visitIflt(Iflt _iflt);

        void visitIfge(Ifge _ifge);

        void visitIfgt(Ifgt _ifgt);

        void visitIfle(Ifle _ifle);

        void visitIfnonnull(Ifnonnull _ifnonnull);

        void visitIfnull(Ifnull _ifnull);

        void visitIinc(Iinc _iinc);

        void visitIload(Iload _iload);

        void visitIload_0(Iload_0 _iload_0);

        void visitIload_1(Iload_1 _iload_1);

        void visitIload_2(Iload_2 _iload_2);

        void visitIload_3(Iload_3 _iload_3);

        void visitImul(Imul _imul);

        void visitIneg(Ineg _ineg);

        void visitInstanceof(Instanceof _instanceof);

        void visitInvokedynamic(Invokedynamic _invokedynamic);

        void visitInvokeinterface(Invokeinterface _invokeinterface);

        void visitInvokespecial(Invokespecial _invokespecial);

        void visitInvokestatic(Invokestatic _invokestatic);

        void visitInvokevirtual(Invokevirtual _invokevirtual);

        void visitIor(Ior _ior);

        void visitIrem(Irem _irem);

        void visitIreturn(Ireturn _ireturn);

        void visitIshl(Ishl _ishl);

        void visitIshr(Ishr _ishr);

        void visitIstore(Istore _istore);

        void visitIstore_0(Istore_0 _istore_0);

        void visitIstore_1(Istore_1 _istore_1);

        void visitIstore_2(Istore_2 _istore_2);

        void visitIstore_3(Istore_3 _istore_3);

        void visitIsub(Isub _isub);

        void visitIushr(Iushr _iushr);

        void visitIxor(Ixor _ixor);

        void visitJsr(Jsr _jsr);

        void visitJsr_w(Jsr_w _jsr_w);

        void visitL2d(L2d _l2d);

        void visitL2f(L2f _l2f);

        void visitL2i(L2i _l2i);

        void visitLadd(Ladd _ladd);

        void visitLaload(Laload _laload);

        void visitLand(Land _land);

        void visitLastore(Lastore _lastore);

        void visitLcmp(Lcmp _lcmp);

        void visitLconst_0(Lconst_0 _lconst_0);

        void visitLconst_1(Lconst_1 _lconst_1);

        void visitLdc(Ldc _ldc);

        void visitLdc_w(Ldc_w _ldc_w);

        void visitLdc2_w(Ldc2_w _ldc2_w);

        void visitLdiv(Ldiv _ldiv);

        void visitLload(Lload _lload);

        void visitLload_0(Lload_0 _lload_0);

        void visitLload_1(Lload_1 _lload_1);

        void visitLload_2(Lload_2 _lload_2);

        void visitLload_3(Lload_3 _lload_3);

        void visitLmul(Lmul _lmul);

        void visitLneg(Lneg _lneg);

        void visitLookupswitch(Lookupswitch _lookupswitch);

        void visitLor(Lor _lor);

        void visitLrem(Lrem _lrem);

        void visitLreturn(Lreturn _lreturn);

        void visitLshl(Lshl _lshl);

        void visitLshr(Lshr _lshr);

        void visitLstore(Lstore _lstore);

        void visitLstore_0(Lstore_0 _lstore_0);

        void visitLstore_1(Lstore_1 _lstore_1);

        void visitLstore_2(Lstore_2 _lstore_2);

        void visitLstore_3(Lstore_3 _lstore_3);

        void visitLsub(Lsub _lsub);

        void visitLushr(Lushr _lushr);

        void visitLxor(Lxor _lxor);

        void visitMonitorenter(Monitorenter _monitorenter);

        void visitMonitorexit(Monitorexit _monitorexit);

        void visitMultianewarray(Multianewarray _multianewarray);

        void visitNew(New _new);

        void visitNewarray(Newarray _newarray);

        void visitNop(Nop _nop);

        void visitPop(Pop _pop);

        void visitPop2(Pop2 _pop2);

        void visitPutfield(Putfield _putfield);

        void visitPutstatic(Putstatic _putstatic);

        void visitRet(Ret _ret);

        void visitReturn(Return _return);

        void visitSaload(Saload _saload);

        void visitSastore(Sastore _sastore);

        void visitSipush(Sipush _sipush);

        void visitSwap(Swap _swap);

        void visitTableswitch(Tableswitch _tableswitch);

        void visitWide(Wide _wide);
    }

    public abstract void accept(Visitor visitor);

    public static class Aaload extends OpCodes {

        Aaload() {
            super(50, "aaload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAaload(this);
        }
    }

    public static class Aastore extends OpCodes {

        Aastore() {
            super(83, "aastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAastore(this);
        }
    }

    public static class Aconst_null extends OpCodes {

        Aconst_null() {
            super(1, "aconst_null");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAconst_null(this);
        }
    }

    public static class Aload extends OpCodes {

        Aload() {
            super(25, "aload");
        }

        @Getter
        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAload(this);
        }
    }

    public static class Aload_0 extends OpCodes {

        Aload_0() {
            super(42, "aload_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAload_0(this);
        }
    }

    public static class Aload_1 extends OpCodes {

        Aload_1() {
            super(43, "aload_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAload_1(this);
        }
    }

    public static class Aload_2 extends OpCodes {

        Aload_2() {
            super(44, "aload_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAload_2(this);
        }
    }

    public static class Aload_3 extends OpCodes {

        Aload_3() {
            super(45, "aload_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAload_3(this);
        }
    }

    public static class Anewarray extends OpCodes {

        Anewarray() {
            super(189, "anewarray");
        }

        @Getter
        byte indexbyte1;
        @Getter
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAnewarray(this);
        }
    }

    public static class Areturn extends OpCodes {

        Areturn() {
            super(176, "areturn");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAreturn(this);
        }
    }

    public static class Arraylength extends OpCodes {

        Arraylength() {
            super(190, "arraylength");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitArraylength(this);
        }
    }

    public static class Astore extends OpCodes {

        Astore() {
            super(58, "astore");
        }

        @Getter
        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAstore(this);
        }
    }

    public static class Astore_0 extends OpCodes {

        Astore_0() {
            super(75, "astore_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAstore_0(this);
        }
    }

    public static class Astore_1 extends OpCodes {

        Astore_1() {
            super(76, "astore_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAstore_1(this);
        }
    }

    public static class Astore_2 extends OpCodes {

        Astore_2() {
            super(77, "astore_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAstore_2(this);
        }
    }

    public static class Astore_3 extends OpCodes {

        Astore_3() {
            super(78, "astore_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAstore_3(this);
        }
    }

    public static class Athrow extends OpCodes {

        Athrow() {
            super(191, "athrow");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitAthrow(this);
        }
    }

    public static class Baload extends OpCodes {

        Baload() {
            super(51, "baload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBaload(this);
        }
    }

    public static class Bastore extends OpCodes {

        Bastore() {
            super(84, "bastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBastore(this);
        }
    }

    public static class Bipush extends OpCodes {

        Bipush() {
            super(16, "bipush");
        }

        @Getter
        byte bb;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitBipush(this);
        }
    }

    public static class Caload extends OpCodes {

        Caload() {
            super(52, "caload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCaload(this);
        }
    }

    public static class Castore extends OpCodes {

        Castore() {
            super(85, "castore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCastore(this);
        }
    }

    public static class Checkcast extends OpCodes {

        Checkcast() {
            super(192, "checkcast");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitCheckcast(this);
        }
    }

    public static class D2f extends OpCodes {

        D2f() {
            super(144, "d2f");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitD2f(this);
        }
    }

    public static class D2i extends OpCodes {

        D2i() {
            super(142, "d2i");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitD2i(this);
        }
    }

    public static class D2l extends OpCodes {

        D2l() {
            super(143, "d2l");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitD2l(this);
        }
    }

    public static class Dadd extends OpCodes {

        Dadd() {
            super(99, "dadd");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDadd(this);
        }
    }

    public static class Dalod extends OpCodes {

        Dalod() {
            super(49, "dalod");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDalod(this);
        }
    }

    public static class Dastore extends OpCodes {

        Dastore() {
            super(82, "dastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDastore(this);
        }
    }

    public static class Dcmpg extends OpCodes {

        Dcmpg() {
            super(152, "dcmpg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDcmpg(this);
        }
    }

    public static class Dcmpl extends OpCodes {

        Dcmpl() {
            super(151, "dcmpl");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDcmpl(this);
        }
    }

    public static class Dconst_0 extends OpCodes {

        Dconst_0() {
            super(14, "dconst_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDconst_0(this);
        }
    }

    public static class Dconst_1 extends OpCodes {

        Dconst_1() {
            super(15, "dconst_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDconst_1(this);
        }
    }

    public static class Ddiv extends OpCodes {

        Ddiv() {
            super(111, "ddiv");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDdiv(this);
        }
    }

    public static class Dload extends OpCodes {

        Dload() {
            super(24, "dload");
        }

        @Getter
        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDload(this);
        }
    }

    public static class Dload_0 extends OpCodes {

        Dload_0() {
            super(38, "dload_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDload_0(this);
        }
    }

    public static class Dload_1 extends OpCodes {

        Dload_1() {
            super(39, "dload_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDload_1(this);
        }
    }

    public static class Dload_2 extends OpCodes {

        Dload_2() {
            super(40, "dload_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDload_2(this);
        }
    }

    public static class Dload_3 extends OpCodes {

        Dload_3() {
            super(41, "dload_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDload_3(this);
        }
    }

    public static class Dmul extends OpCodes {

        Dmul() {
            super(107, "dmul");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDmul(this);
        }
    }

    public static class Dneg extends OpCodes {

        Dneg() {
            super(119, "dneg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDneg(this);
        }
    }

    public static class Drem extends OpCodes {

        Drem() {
            super(115, "drem");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDrem(this);
        }
    }

    public static class Dreturn extends OpCodes {

        Dreturn() {
            super(175, "dreturn");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDreturn(this);
        }
    }

    public static class Dstore extends OpCodes {

        Dstore() {
            super(57, "dstore");
        }

        @Getter
        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDstore(this);
        }
    }

    public static class Dstore_0 extends OpCodes {

        Dstore_0() {
            super(71, "dstore_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDstore_0(this);
        }
    }

    public static class Dstore_1 extends OpCodes {

        Dstore_1() {
            super(72, "dstore_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDstore_1(this);
        }
    }

    public static class Dstore_2 extends OpCodes {

        Dstore_2() {
            super(73, "dstore_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDstore_2(this);
        }
    }

    public static class Dstore_3 extends OpCodes {

        Dstore_3() {
            super(74, "dstore_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDstore_3(this);
        }
    }

    public static class Dsub extends OpCodes {

        Dsub() {
            super(103, "dsub");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDsub(this);
        }
    }

    public static class Dup extends OpCodes {

        Dup() {
            super(89, "dup");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup(this);
        }
    }

    public static class Dup_x1 extends OpCodes {

        Dup_x1() {
            super(90, "dup_x1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup_x1(this);
        }
    }

    public static class Dup_x2 extends OpCodes {

        Dup_x2() {
            super(91, "dup_x2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup_x2(this);
        }
    }

    public static class Dup2 extends OpCodes {

        Dup2() {
            super(92, "dup2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup2(this);
        }
    }

    public static class Dup2_x1 extends OpCodes {

        Dup2_x1() {
            super(93, "dup2_x1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup2_x1(this);
        }
    }

    public static class Dup2_x2 extends OpCodes {

        Dup2_x2() {
            super(94, "dup2_x2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitDup2_x2(this);
        }
    }

    public static class F2d extends OpCodes {

        F2d() {
            super(141, "f2d");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitF2d(this);
        }
    }

    public static class F2i extends OpCodes {

        F2i() {
            super(139, "f2i");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitF2i(this);
        }
    }

    public static class F2l extends OpCodes {

        F2l() {
            super(140, "f2l");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitF2l(this);
        }
    }

    public static class Fadd extends OpCodes {

        Fadd() {
            super(98, "fadd");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFadd(this);
        }
    }

    public static class Faload extends OpCodes {

        Faload() {
            super(48, "faload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFaload(this);
        }
    }

    public static class Fastore extends OpCodes {

        Fastore() {
            super(81, "fastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFastore(this);
        }
    }

    public static class Fcmpg extends OpCodes {

        Fcmpg() {
            super(150, "fcmpg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFcmpg(this);
        }
    }

    public static class Fcmpl extends OpCodes {

        Fcmpl() {
            super(149, "fcmpl");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFcmpl(this);
        }
    }

    public static class Fconst_0 extends OpCodes {

        Fconst_0() {
            super(11, "fconst_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFconst_0(this);
        }
    }

    public static class Fconst_1 extends OpCodes {

        Fconst_1() {
            super(12, "fconst_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFconst_1(this);
        }
    }

    public static class Fconst_2 extends OpCodes {

        Fconst_2() {
            super(13, "fconst_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFconst_2(this);
        }
    }

    public static class Fdiv extends OpCodes {

        Fdiv() {
            super(110, "fdiv");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFdiv(this);
        }
    }

    public static class Fload extends OpCodes {

        Fload() {
            super(23, "fload");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFload(this);
        }
    }

    public static class Fload_0 extends OpCodes {

        Fload_0() {
            super(34, "fload_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFload_0(this);
        }
    }

    public static class Fload_1 extends OpCodes {

        Fload_1() {
            super(35, "fload_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFload_1(this);
        }
    }

    public static class Fload_2 extends OpCodes {

        Fload_2() {
            super(36, "fload_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFload_2(this);
        }
    }

    public static class Fload_3 extends OpCodes {

        Fload_3() {
            super(37, "fload_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFload_3(this);
        }
    }

    public static class Fmul extends OpCodes {

        Fmul() {
            super(106, "fmul");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFmul(this);
        }
    }

    public static class Fneg extends OpCodes {

        Fneg() {
            super(118, "fneg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFneg(this);
        }
    }

    public static class Frem extends OpCodes {

        Frem() {
            super(114, "frem");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFrem(this);
        }
    }

    public static class Freturn extends OpCodes {

        Freturn() {
            super(174, "freturn");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFreturn(this);
        }
    }

    public static class Fstore extends OpCodes {

        Fstore() {
            super(56, "fstore");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFstore(this);
        }
    }

    public static class Fstore_0 extends OpCodes {

        Fstore_0() {
            super(67, "fstore_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFstore_0(this);
        }
    }

    public static class Fstore_1 extends OpCodes {

        Fstore_1() {
            super(68, "fstore_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFstore_1(this);
        }
    }

    public static class Fstore_2 extends OpCodes {

        Fstore_2() {
            super(69, "fstore_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFstore_2(this);
        }
    }

    public static class Fstore_3 extends OpCodes {

        Fstore_3() {
            super(70, "fstore_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFstore_3(this);
        }
    }

    public static class Fsub extends OpCodes {

        Fsub() {
            super(102, "fsub");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitFsub(this);
        }
    }

    public static class Getfield extends OpCodes {

        Getfield() {
            super(180, "getfield");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGetfield(this);
        }
    }

    public static class Getstatic extends OpCodes {

        Getstatic() {
            super(178, "getstatic");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGetstatic(this);
        }
    }

    public static class Goto extends OpCodes {

        Goto() {
            super(167, "goto");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGoto(this);
        }
    }

    public static class Goto_w extends OpCodes {

        Goto_w() {
            super(200, "goto_w");
        }

        byte branchbyte1;
        byte branchbyte2;
        byte branchbyte3;
        byte branchbyte4;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitGoto_w(this);
        }
    }

    public static class I2b extends OpCodes {

        I2b() {
            super(145, "i2b");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2b(this);
        }
    }

    public static class I2c extends OpCodes {

        I2c() {
            super(146, "i2c");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2c(this);
        }
    }

    public static class I2d extends OpCodes {

        I2d() {
            super(135, "i2d");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2d(this);
        }
    }

    public static class I2f extends OpCodes {

        I2f() {
            super(134, "i2f");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2f(this);
        }
    }

    public static class I2l extends OpCodes {

        I2l() {
            super(133, "i2l");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2l(this);
        }
    }

    public static class I2s extends OpCodes {

        I2s() {
            super(147, "i2s");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitI2s(this);
        }
    }

    public static class Iadd extends OpCodes {

        Iadd() {
            super(96, "iadd");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIadd(this);
        }
    }

    public static class Iaload extends OpCodes {

        Iaload() {
            super(46, "iaload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIaload(this);
        }
    }

    public static class Iand extends OpCodes {

        Iand() {
            super(126, "iand");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIand(this);
        }
    }

    public static class Iastore extends OpCodes {

        Iastore() {
            super(79, "iastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIastore(this);
        }
    }

    public static class Iconst_m1 extends OpCodes {

        Iconst_m1() {
            super(2, "iconst_m1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_m1(this);
        }
    }

    public static class Iconst_0 extends OpCodes {

        Iconst_0() {
            super(3, "iconst_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_0(this);
        }
    }

    public static class Iconst_1 extends OpCodes {

        Iconst_1() {
            super(4, "iconst_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_1(this);
        }
    }

    public static class Iconst_2 extends OpCodes {

        Iconst_2() {
            super(5, "iconst_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_2(this);
        }
    }

    public static class Iconst_3 extends OpCodes {

        Iconst_3() {
            super(6, "iconst_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_3(this);
        }
    }

    public static class Iconst_4 extends OpCodes {

        Iconst_4() {
            super(7, "iconst_4");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_4(this);
        }
    }

    public static class Iconst_5 extends OpCodes {

        Iconst_5() {
            super(8, "iconst_5");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIconst_5(this);
        }
    }

    public static class Idiv extends OpCodes {

        Idiv() {
            super(108, "idiv");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIdiv(this);
        }
    }

    public static class If_acmpeq extends OpCodes {

        If_acmpeq() {
            super(165, "if_acmpeq");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_acmpeq(this);
        }
    }

    public static class If_acmpne extends OpCodes {

        If_acmpne() {
            super(166, "if_acmpne");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_acmpne(this);
        }
    }

    public static class If_icmpeq extends OpCodes {

        If_icmpeq() {
            super(159, "if_icmpeq");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmpeq(this);
        }
    }

    public static class If_icmpne extends OpCodes {

        If_icmpne() {
            super(160, "if_icmpne");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmpne(this);
        }
    }

    public static class If_icmplt extends OpCodes {

        If_icmplt() {
            super(161, "if_icmplt");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmplt(this);
        }
    }

    public static class If_icmpge extends OpCodes {

        If_icmpge() {
            super(162, "if_icmpge");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmpge(this);
        }
    }

    public static class If_icmpgt extends OpCodes {

        If_icmpgt() {
            super(163, "if_icmpgt");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmpgt(this);
        }
    }

    public static class If_icmple extends OpCodes {

        If_icmple() {
            super(164, "if_icmple");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIf_icmple(this);
        }
    }

    public static class Ifeq extends OpCodes {

        Ifeq() {
            super(153, "ifeq");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfeq(this);
        }
    }

    public static class Ifne extends OpCodes {

        Ifne() {
            super(154, "ifne");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfne(this);
        }
    }

    public static class Iflt extends OpCodes {

        Iflt() {
            super(155, "iflt");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIflt(this);
        }
    }

    public static class Ifge extends OpCodes {

        Ifge() {
            super(156, "ifge");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfge(this);
        }
    }

    public static class Ifgt extends OpCodes {

        Ifgt() {
            super(157, "ifgt");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfgt(this);
        }
    }

    public static class Ifle extends OpCodes {

        Ifle() {
            super(158, "ifle");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfle(this);
        }
    }

    public static class Ifnonnull extends OpCodes {

        Ifnonnull() {
            super(199, "ifnonnull");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfnonnull(this);
        }
    }

    public static class Ifnull extends OpCodes {

        Ifnull() {
            super(198, "ifnull");
        }

        byte branchbyte1;
        byte branchbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIfnull(this);
        }
    }

    public static class Iinc extends OpCodes {

        Iinc() {
            super(132, "iinc");
        }

        byte index;
        byte constbyte;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIinc(this);
        }
    }

    public static class Iload extends OpCodes {

        Iload() {
            super(21, "iload");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIload(this);
        }
    }

    public static class Iload_0 extends OpCodes {

        Iload_0() {
            super(26, "iload_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIload_0(this);
        }
    }

    public static class Iload_1 extends OpCodes {

        Iload_1() {
            super(27, "iload_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIload_1(this);
        }
    }

    public static class Iload_2 extends OpCodes {

        Iload_2() {
            super(28, "iload_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIload_2(this);
        }
    }

    public static class Iload_3 extends OpCodes {

        Iload_3() {
            super(29, "iload_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIload_3(this);
        }
    }

    public static class Imul extends OpCodes {

        Imul() {
            super(104, "imul");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitImul(this);
        }
    }

    public static class Ineg extends OpCodes {

        Ineg() {
            super(116, "ineg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIneg(this);
        }
    }

    public static class Instanceof extends OpCodes {

        Instanceof() {
            super(193, "instanceof");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInstanceof(this);
        }
    }

    public static class Invokedynamic extends OpCodes {

        Invokedynamic() {
            super(186, "invokedynamic");
        }

        byte indexbyte1;
        byte indexbyte2;
        byte indexbyte3;
        byte indexbyte4;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInvokedynamic(this);
        }
    }

    public static class Invokeinterface extends OpCodes {

        Invokeinterface() {
            super(185, "invokeinterface");
        }

        byte indexbyte1;
        byte indexbyte2;
        byte count;
        byte zero;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInvokeinterface(this);
        }
    }

    public static class Invokespecial extends OpCodes {

        Invokespecial() {
            super(183, "invokespecial");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInvokespecial(this);
        }
    }

    public static class Invokestatic extends OpCodes {

        Invokestatic() {
            super(184, "invokestatic");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInvokestatic(this);
        }
    }

    @Getter
    public static class Invokevirtual extends OpCodes {

        Invokevirtual() {
            super(182, "invokevirtual");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitInvokevirtual(this);
        }
    }

    public static class Ior extends OpCodes {

        Ior() {
            super(128, "ior");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIor(this);
        }
    }

    public static class Irem extends OpCodes {

        Irem() {
            super(112, "irem");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIrem(this);
        }
    }

    public static class Ireturn extends OpCodes {

        Ireturn() {
            super(172, "ireturn");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIreturn(this);
        }
    }

    public static class Ishl extends OpCodes {

        Ishl() {
            super(120, "ishl");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIshl(this);
        }
    }

    public static class Ishr extends OpCodes {

        Ishr() {
            super(122, "ishr");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIshr(this);
        }
    }

    public static class Istore extends OpCodes {

        Istore() {
            super(54, "istore");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIstore(this);
        }
    }

    public static class Istore_0 extends OpCodes {

        Istore_0() {
            super(59, "istore_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIstore_0(this);
        }
    }

    public static class Istore_1 extends OpCodes {

        Istore_1() {
            super(60, "istore_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIstore_1(this);
        }
    }

    public static class Istore_2 extends OpCodes {

        Istore_2() {
            super(61, "istore_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIstore_2(this);
        }
    }

    public static class Istore_3 extends OpCodes {

        Istore_3() {
            super(62, "istore_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIstore_3(this);
        }
    }

    public static class Isub extends OpCodes {

        Isub() {
            super(100, "isub");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIsub(this);
        }
    }

    public static class Iushr extends OpCodes {

        Iushr() {
            super(124, "iushr");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIushr(this);
        }
    }

    public static class Ixor extends OpCodes {

        Ixor() {
            super(130, "ixor");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitIxor(this);
        }
    }

    public static class Jsr extends OpCodes {

        Jsr() {
            super(168, "jsr");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitJsr(this);
        }
    }

    public static class Jsr_w extends OpCodes {

        Jsr_w() {
            super(201, "jsr_w");
        }

        byte branchbyte1;
        byte branchbyte2;
        byte branchbyte3;
        byte branchbyte4;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitJsr_w(this);
        }
    }

    public static class L2d extends OpCodes {

        L2d() {
            super(138, "l2d");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitL2d(this);
        }
    }

    public static class L2f extends OpCodes {

        L2f() {
            super(137, "l2f");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitL2f(this);
        }
    }

    public static class L2i extends OpCodes {

        L2i() {
            super(136, "l2i");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitL2i(this);
        }
    }

    public static class Ladd extends OpCodes {

        Ladd() {
            super(97, "ladd");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLadd(this);
        }
    }

    public static class Laload extends OpCodes {

        Laload() {
            super(47, "laload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLaload(this);
        }
    }

    public static class Land extends OpCodes {

        Land() {
            super(127, "land");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLand(this);
        }
    }

    public static class Lastore extends OpCodes {

        Lastore() {
            super(80, "lastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLastore(this);
        }
    }

    public static class Lcmp extends OpCodes {

        Lcmp() {
            super(148, "lcmp");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLcmp(this);
        }
    }

    public static class Lconst_0 extends OpCodes {

        Lconst_0() {
            super(9, "lconst_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLconst_0(this);
        }
    }

    public static class Lconst_1 extends OpCodes {

        Lconst_1() {
            super(10, "lconst_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLconst_1(this);
        }
    }

    public static class Ldc extends OpCodes {

        Ldc() {
            super(18, "ldc");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLdc(this);
        }
    }

    public static class Ldc_w extends OpCodes {

        Ldc_w() {
            super(19, "ldc_w");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLdc_w(this);
        }
    }

    public static class Ldc2_w extends OpCodes {

        Ldc2_w() {
            super(20, "ldc2_w");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLdc2_w(this);
        }
    }

    public static class Ldiv extends OpCodes {

        Ldiv() {
            super(109, "ldiv");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLdiv(this);
        }
    }

    public static class Lload extends OpCodes {

        Lload() {
            super(22, "lload");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLload(this);
        }
    }

    public static class Lload_0 extends OpCodes {

        Lload_0() {
            super(30, "lload_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLload_0(this);
        }
    }

    public static class Lload_1 extends OpCodes {

        Lload_1() {
            super(31, "lload_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLload_1(this);
        }
    }

    public static class Lload_2 extends OpCodes {

        Lload_2() {
            super(32, "lload_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLload_2(this);
        }
    }

    public static class Lload_3 extends OpCodes {

        Lload_3() {
            super(33, "lload_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLload_3(this);
        }
    }

    public static class Lmul extends OpCodes {

        Lmul() {
            super(105, "lmul");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLmul(this);
        }
    }

    public static class Lneg extends OpCodes {

        Lneg() {
            super(117, "lneg");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLneg(this);
        }
    }

    public static class Lookupswitch extends OpCodes {

        Lookupswitch() {
            super(171, "lookupswitch");
        }

        int defaultbyte1;
        int defaultbyte2;
        int defaultbyte3;
        int defaultbyte4;
        int npairs1;
        int npairs2;
        int npairs3;
        int npairs4;
        int[] matchOffsetsPairs;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLookupswitch(this);
        }
    }

    public static class Lor extends OpCodes {

        Lor() {
            super(129, "lor");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLor(this);
        }
    }

    public static class Lrem extends OpCodes {

        Lrem() {
            super(113, "lrem");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLrem(this);
        }
    }

    public static class Lreturn extends OpCodes {

        Lreturn() {
            super(173, "lreturn");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLreturn(this);
        }
    }

    public static class Lshl extends OpCodes {

        Lshl() {
            super(121, "lshl");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLshl(this);
        }
    }

    public static class Lshr extends OpCodes {

        Lshr() {
            super(123, "lshr");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLshr(this);
        }
    }

    public static class Lstore extends OpCodes {

        Lstore() {
            super(55, "lstore");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLstore(this);
        }
    }

    public static class Lstore_0 extends OpCodes {

        Lstore_0() {
            super(63, "lstore_0");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLstore_0(this);
        }
    }

    public static class Lstore_1 extends OpCodes {

        Lstore_1() {
            super(64, "lstore_1");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLstore_1(this);
        }
    }

    public static class Lstore_2 extends OpCodes {

        Lstore_2() {
            super(65, "lstore_2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLstore_2(this);
        }
    }

    public static class Lstore_3 extends OpCodes {

        Lstore_3() {
            super(66, "lstore_3");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLstore_3(this);
        }
    }

    public static class Lsub extends OpCodes {

        Lsub() {
            super(101, "lsub");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLsub(this);
        }
    }

    public static class Lushr extends OpCodes {

        Lushr() {
            super(125, "lushr");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLushr(this);
        }
    }

    public static class Lxor extends OpCodes {

        Lxor() {
            super(131, "lxor");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitLxor(this);
        }
    }

    public static class Monitorenter extends OpCodes {

        Monitorenter() {
            super(194, "monitorenter");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitMonitorenter(this);
        }
    }

    public static class Monitorexit extends OpCodes {

        Monitorexit() {
            super(195, "monitorexit");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitMonitorexit(this);
        }
    }

    public static class Multianewarray extends OpCodes {

        Multianewarray() {
            super(197, "multianewarray");
        }

        byte indexbyte1;
        byte indexbyte2;
        byte dims;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitMultianewarray(this);
        }
    }

    public static class New extends OpCodes {

        New() {
            super(187, "new");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitNew(this);
        }
    }

    public static class Newarray extends OpCodes {

        Newarray() {
            super(188, "newarray");
        }

        byte atype;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitNewarray(this);
        }
    }

    public static class Nop extends OpCodes {

        Nop() {
            super(0, "nop");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitNop(this);
        }
    }

    public static class Pop extends OpCodes {

        Pop() {
            super(87, "pop");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitPop(this);
        }
    }

    public static class Pop2 extends OpCodes {

        Pop2() {
            super(88, "pop2");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitPop2(this);
        }
    }

    public static class Putfield extends OpCodes {

        Putfield() {
            super(181, "putfield");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitPutfield(this);
        }
    }

    public static class Putstatic extends OpCodes {

        Putstatic() {
            super(179, "putstatic");
        }

        byte indexbyte1;
        byte indexbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitPutstatic(this);
        }
    }

    public static class Ret extends OpCodes {

        Ret() {
            super(169, "ret");
        }

        byte index;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitRet(this);
        }
    }

    public static class Return extends OpCodes {

        Return() {
            super(177, "return");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitReturn(this);
        }
    }

    public static class Saload extends OpCodes {

        Saload() {
            super(53, "saload");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSaload(this);
        }
    }

    public static class Sastore extends OpCodes {

        Sastore() {
            super(86, "sastore");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSastore(this);
        }
    }

    public static class Sipush extends OpCodes {

        Sipush() {
            super(17, "sipush");
        }

        byte sbyte1;
        byte sbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSipush(this);
        }
    }

    public static class Swap extends OpCodes {

        Swap() {
            super(95, "swap");
        }

        @Override
        public void accept(Visitor visitor) {
            visitor.visitSwap(this);
        }
    }

    public static class Tableswitch extends OpCodes {

        Tableswitch() {
            super(170, "tableswitch");
        }

        int defaultbyte1;
        int defaultbyte2;
        int defaultbyte3;
        int defaultbyte4;
        int lowbyte1;
        int lowbyte2;
        int lowbyte3;
        int lowbyte4;
        int highbyte1;
        int highbyte2;
        int highbyte3;
        int highbyte4;
        int[] offsets;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitTableswitch(this);
        }
    }

    public static class Wide extends OpCodes {

        Wide() {
            super(196, "wide");
        }

        OpCodes opcode;
        byte indexbyte1;
        byte indexbyte2;
        byte constbyte1;
        byte constbyte2;

        @Override
        public void accept(Visitor visitor) {
            visitor.visitWide(this);
        }
    }
}
