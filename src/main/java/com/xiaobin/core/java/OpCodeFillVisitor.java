package com.xiaobin.core.java;

import com.xiaobin.core.util.ByteReader;

/**
 * created by xuweibin at 2025/1/10 16:34
 */
class OpCodeFillVisitor implements OpCodes.Visitor {

    private final ByteReader byteReader;

    OpCodeFillVisitor(ByteReader byteReader) {
        this.byteReader = byteReader;
    }

    @Override
    public void visitAaload(OpCodes.Aaload _aaload) {

    }

    @Override
    public void visitAastore(OpCodes.Aastore _aastore) {

    }

    @Override
    public void visitAconst_null(OpCodes.Aconst_null _aconst_null) {

    }

    @Override
    public void visitAload(OpCodes.Aload _aload) {
        _aload.index = byteReader.read1();
    }

    @Override
    public void visitAload_0(OpCodes.Aload_0 _aload_0) {

    }

    @Override
    public void visitAload_1(OpCodes.Aload_1 _aload_1) {

    }

    @Override
    public void visitAload_2(OpCodes.Aload_2 _aload_2) {

    }

    @Override
    public void visitAload_3(OpCodes.Aload_3 _aload_3) {

    }

    @Override
    public void visitAnewarray(OpCodes.Anewarray _anewarray) {
        _anewarray.indexbyte1 = byteReader.read1();
        _anewarray.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitAreturn(OpCodes.Areturn _areturn) {

    }

    @Override
    public void visitArraylength(OpCodes.Arraylength _arraylength) {

    }

    @Override
    public void visitAstore(OpCodes.Astore _astore) {
        _astore.index = byteReader.read1();
    }

    @Override
    public void visitAstore_0(OpCodes.Astore_0 _astore_0) {

    }

    @Override
    public void visitAstore_1(OpCodes.Astore_1 _astore_1) {

    }

    @Override
    public void visitAstore_2(OpCodes.Astore_2 _astore_2) {

    }

    @Override
    public void visitAstore_3(OpCodes.Astore_3 _astore_3) {

    }

    @Override
    public void visitAthrow(OpCodes.Athrow _athrow) {

    }

    @Override
    public void visitBaload(OpCodes.Baload _baload) {

    }

    @Override
    public void visitBastore(OpCodes.Bastore _bastore) {

    }

    @Override
    public void visitBipush(OpCodes.Bipush _bipush) {
        _bipush.bb = byteReader.read1();
    }

    @Override
    public void visitCaload(OpCodes.Caload _caload) {

    }

    @Override
    public void visitCastore(OpCodes.Castore _castore) {

    }

    @Override
    public void visitCheckcast(OpCodes.Checkcast _checkcast) {
        _checkcast.indexbyte1 = byteReader.read1();
        _checkcast.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitD2f(OpCodes.D2f _d2f) {

    }

    @Override
    public void visitD2i(OpCodes.D2i _d2i) {

    }

    @Override
    public void visitD2l(OpCodes.D2l _d2l) {

    }

    @Override
    public void visitDadd(OpCodes.Dadd _dadd) {

    }

    @Override
    public void visitDalod(OpCodes.Dalod _dalod) {

    }

    @Override
    public void visitDastore(OpCodes.Dastore _dastore) {

    }

    @Override
    public void visitDcmpg(OpCodes.Dcmpg _dcmpg) {

    }

    @Override
    public void visitDcmpl(OpCodes.Dcmpl _dcmpl) {

    }

    @Override
    public void visitDconst_0(OpCodes.Dconst_0 _dconst_0) {

    }

    @Override
    public void visitDconst_1(OpCodes.Dconst_1 _dconst_1) {

    }

    @Override
    public void visitDdiv(OpCodes.Ddiv _ddiv) {

    }

    @Override
    public void visitDload(OpCodes.Dload _dload) {
        _dload.index = byteReader.read1();
    }

    @Override
    public void visitDload_0(OpCodes.Dload_0 _dload_0) {

    }

    @Override
    public void visitDload_1(OpCodes.Dload_1 _dload_1) {

    }

    @Override
    public void visitDload_2(OpCodes.Dload_2 _dload_2) {

    }

    @Override
    public void visitDload_3(OpCodes.Dload_3 _dload_3) {

    }

    @Override
    public void visitDmul(OpCodes.Dmul _dmul) {

    }

    @Override
    public void visitDneg(OpCodes.Dneg _dneg) {

    }

    @Override
    public void visitDrem(OpCodes.Drem _drem) {

    }

    @Override
    public void visitDreturn(OpCodes.Dreturn _dreturn) {

    }

    @Override
    public void visitDstore(OpCodes.Dstore _dstore) {
        _dstore.index = byteReader.read1();
    }

    @Override
    public void visitDstore_0(OpCodes.Dstore_0 _dstore_0) {

    }

    @Override
    public void visitDstore_1(OpCodes.Dstore_1 _dstore_1) {

    }

    @Override
    public void visitDstore_2(OpCodes.Dstore_2 _dstore_2) {

    }

    @Override
    public void visitDstore_3(OpCodes.Dstore_3 _dstore_3) {

    }

    @Override
    public void visitDsub(OpCodes.Dsub _dsub) {

    }

    @Override
    public void visitDup(OpCodes.Dup _dup) {

    }

    @Override
    public void visitDup_x1(OpCodes.Dup_x1 _dup_x1) {

    }

    @Override
    public void visitDup_x2(OpCodes.Dup_x2 _dup_x2) {

    }

    @Override
    public void visitDup2(OpCodes.Dup2 _dup2) {

    }

    @Override
    public void visitDup2_x1(OpCodes.Dup2_x1 _dup2_x1) {

    }

    @Override
    public void visitDup2_x2(OpCodes.Dup2_x2 _dup2_x2) {

    }

    @Override
    public void visitF2d(OpCodes.F2d _f2d) {

    }

    @Override
    public void visitF2i(OpCodes.F2i _f2i) {

    }

    @Override
    public void visitF2l(OpCodes.F2l _f2l) {

    }

    @Override
    public void visitFadd(OpCodes.Fadd _fadd) {

    }

    @Override
    public void visitFaload(OpCodes.Faload _faload) {

    }

    @Override
    public void visitFastore(OpCodes.Fastore _fastore) {

    }

    @Override
    public void visitFcmpg(OpCodes.Fcmpg _fcmpg) {

    }

    @Override
    public void visitFcmpl(OpCodes.Fcmpl _fcmpl) {

    }

    @Override
    public void visitFconst_0(OpCodes.Fconst_0 _fconst_0) {

    }

    @Override
    public void visitFconst_1(OpCodes.Fconst_1 _fconst_1) {

    }

    @Override
    public void visitFconst_2(OpCodes.Fconst_2 _fconst_2) {

    }

    @Override
    public void visitFdiv(OpCodes.Fdiv _fdiv) {

    }

    @Override
    public void visitFload(OpCodes.Fload _fload) {
        _fload.index = byteReader.read1();
    }

    @Override
    public void visitFload_0(OpCodes.Fload_0 _fload_0) {

    }

    @Override
    public void visitFload_1(OpCodes.Fload_1 _fload_1) {

    }

    @Override
    public void visitFload_2(OpCodes.Fload_2 _fload_2) {

    }

    @Override
    public void visitFload_3(OpCodes.Fload_3 _fload_3) {

    }

    @Override
    public void visitFmul(OpCodes.Fmul _fmul) {

    }

    @Override
    public void visitFneg(OpCodes.Fneg _fneg) {

    }

    @Override
    public void visitFrem(OpCodes.Frem _frem) {

    }

    @Override
    public void visitFreturn(OpCodes.Freturn _freturn) {

    }

    @Override
    public void visitFstore(OpCodes.Fstore _fstore) {
        _fstore.index = byteReader.read1();
    }

    @Override
    public void visitFstore_0(OpCodes.Fstore_0 _fstore_0) {

    }

    @Override
    public void visitFstore_1(OpCodes.Fstore_1 _fstore_1) {

    }

    @Override
    public void visitFstore_2(OpCodes.Fstore_2 _fstore_2) {

    }

    @Override
    public void visitFstore_3(OpCodes.Fstore_3 _fstore_3) {

    }

    @Override
    public void visitFsub(OpCodes.Fsub _fsub) {

    }

    @Override
    public void visitGetfield(OpCodes.Getfield _getfield) {
        _getfield.indexbyte1 = byteReader.read1();
        _getfield.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitGetstatic(OpCodes.Getstatic _getstatic) {
        _getstatic.indexbyte1 = byteReader.read1();
        _getstatic.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitGoto(OpCodes.Goto _goto) {
        _goto.indexbyte1 = byteReader.read1();
        _goto.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitGoto_w(OpCodes.Goto_w _goto_w) {
        _goto_w.branchbyte1 = byteReader.read1();
        _goto_w.branchbyte2 = byteReader.read1();
        _goto_w.branchbyte3 = byteReader.read1();
        _goto_w.branchbyte4 = byteReader.read1();
    }

    @Override
    public void visitI2b(OpCodes.I2b _i2b) {

    }

    @Override
    public void visitI2c(OpCodes.I2c _i2c) {

    }

    @Override
    public void visitI2d(OpCodes.I2d _i2d) {

    }

    @Override
    public void visitI2f(OpCodes.I2f _i2f) {

    }

    @Override
    public void visitI2l(OpCodes.I2l _i2l) {

    }

    @Override
    public void visitI2s(OpCodes.I2s _i2s) {

    }

    @Override
    public void visitIadd(OpCodes.Iadd _iadd) {

    }

    @Override
    public void visitIaload(OpCodes.Iaload _iaload) {

    }

    @Override
    public void visitIand(OpCodes.Iand _iand) {

    }

    @Override
    public void visitIastore(OpCodes.Iastore _iastore) {

    }

    @Override
    public void visitIconst_m1(OpCodes.Iconst_m1 _iconst_m1) {

    }

    @Override
    public void visitIconst_0(OpCodes.Iconst_0 _iconst_0) {

    }

    @Override
    public void visitIconst_1(OpCodes.Iconst_1 _iconst_1) {

    }

    @Override
    public void visitIconst_2(OpCodes.Iconst_2 _iconst_2) {

    }

    @Override
    public void visitIconst_3(OpCodes.Iconst_3 _iconst_3) {

    }

    @Override
    public void visitIconst_4(OpCodes.Iconst_4 _iconst_4) {

    }

    @Override
    public void visitIconst_5(OpCodes.Iconst_5 _iconst_5) {

    }

    @Override
    public void visitIdiv(OpCodes.Idiv _idiv) {

    }

    @Override
    public void visitIf_acmpeq(OpCodes.If_acmpeq _if_acmpeq) {
        _if_acmpeq.branchbyte1 = byteReader.read1();
        _if_acmpeq.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_acmpne(OpCodes.If_acmpne _if_acmpne) {
        _if_acmpne.branchbyte1 = byteReader.read1();
        _if_acmpne.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmpeq(OpCodes.If_icmpeq _if_icmpeq) {
        _if_icmpeq.branchbyte1 = byteReader.read1();
        _if_icmpeq.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmpne(OpCodes.If_icmpne _if_icmpne) {
        _if_icmpne.branchbyte1 = byteReader.read1();
        _if_icmpne.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmplt(OpCodes.If_icmplt _if_icmplt) {
        _if_icmplt.branchbyte1 = byteReader.read1();
        _if_icmplt.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmpge(OpCodes.If_icmpge _if_icmpge) {
        _if_icmpge.branchbyte1 = byteReader.read1();
        _if_icmpge.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmpgt(OpCodes.If_icmpgt _if_icmpgt) {
        _if_icmpgt.branchbyte1 = byteReader.read1();
        _if_icmpgt.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIf_icmple(OpCodes.If_icmple _if_icmple) {
        _if_icmple.branchbyte1 = byteReader.read1();
        _if_icmple.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfeq(OpCodes.Ifeq _ifeq) {
        _ifeq.branchbyte1 = byteReader.read1();
        _ifeq.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfne(OpCodes.Ifne _ifne) {
        _ifne.branchbyte1 = byteReader.read1();
        _ifne.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIflt(OpCodes.Iflt _iflt) {
        _iflt.branchbyte1 = byteReader.read1();
        _iflt.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfge(OpCodes.Ifge _ifge) {
        _ifge.branchbyte1 = byteReader.read1();
        _ifge.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfgt(OpCodes.Ifgt _ifgt) {
        _ifgt.branchbyte1 = byteReader.read1();
        _ifgt.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfle(OpCodes.Ifle _ifle) {
        _ifle.branchbyte1 = byteReader.read1();
        _ifle.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfnonnull(OpCodes.Ifnonnull _ifnonnull) {
        _ifnonnull.branchbyte1 = byteReader.read1();
        _ifnonnull.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIfnull(OpCodes.Ifnull _ifnull) {
        _ifnull.branchbyte1 = byteReader.read1();
        _ifnull.branchbyte2 = byteReader.read1();
    }

    @Override
    public void visitIinc(OpCodes.Iinc _iinc) {
        _iinc.index = byteReader.read1();
        _iinc.constbyte = byteReader.read1();
    }

    @Override
    public void visitIload(OpCodes.Iload _iload) {
        _iload.index = byteReader.read1();
    }

    @Override
    public void visitIload_0(OpCodes.Iload_0 _iload_0) {

    }

    @Override
    public void visitIload_1(OpCodes.Iload_1 _iload_1) {

    }

    @Override
    public void visitIload_2(OpCodes.Iload_2 _iload_2) {

    }

    @Override
    public void visitIload_3(OpCodes.Iload_3 _iload_3) {

    }

    @Override
    public void visitImul(OpCodes.Imul _imul) {

    }

    @Override
    public void visitIneg(OpCodes.Ineg _ineg) {

    }

    @Override
    public void visitInstanceof(OpCodes.Instanceof _instanceof) {
        _instanceof.indexbyte1 = byteReader.read1();
        _instanceof.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitInvokedynamic(OpCodes.Invokedynamic _invokedynamic) {
        _invokedynamic.indexbyte1 = byteReader.read1();
        _invokedynamic.indexbyte2 = byteReader.read1();
        _invokedynamic.indexbyte3 = byteReader.read1();
        _invokedynamic.indexbyte4 = byteReader.read1();
    }

    @Override
    public void visitInvokeinterface(OpCodes.Invokeinterface _invokeinterface) {
        _invokeinterface.indexbyte1 = byteReader.read1();
        _invokeinterface.indexbyte2 = byteReader.read1();
        _invokeinterface.count = byteReader.read1();
        _invokeinterface.zero = byteReader.read1();
    }

    @Override
    public void visitInvokespecial(OpCodes.Invokespecial _invokespecial) {
        _invokespecial.indexbyte1 = byteReader.read1();
        _invokespecial.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitInvokestatic(OpCodes.Invokestatic _invokestatic) {
        _invokestatic.indexbyte1 = byteReader.read1();
        _invokestatic.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitInvokevirtual(OpCodes.Invokevirtual _invokevirtual) {
        _invokevirtual.indexbyte1 = byteReader.read1();
        _invokevirtual.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitIor(OpCodes.Ior _ior) {

    }

    @Override
    public void visitIrem(OpCodes.Irem _irem) {

    }

    @Override
    public void visitIreturn(OpCodes.Ireturn _ireturn) {

    }

    @Override
    public void visitIshl(OpCodes.Ishl _ishl) {

    }

    @Override
    public void visitIshr(OpCodes.Ishr _ishr) {

    }

    @Override
    public void visitIstore(OpCodes.Istore _istore) {
        _istore.index = byteReader.read1();
    }

    @Override
    public void visitIstore_0(OpCodes.Istore_0 _istore_0) {

    }

    @Override
    public void visitIstore_1(OpCodes.Istore_1 _istore_1) {

    }

    @Override
    public void visitIstore_2(OpCodes.Istore_2 _istore_2) {

    }

    @Override
    public void visitIstore_3(OpCodes.Istore_3 _istore_3) {

    }

    @Override
    public void visitIsub(OpCodes.Isub _isub) {

    }

    @Override
    public void visitIushr(OpCodes.Iushr _iushr) {

    }

    @Override
    public void visitIxor(OpCodes.Ixor _ixor) {

    }

    @Override
    public void visitJsr(OpCodes.Jsr _jsr) {
        _jsr.indexbyte1 = byteReader.read1();
        _jsr.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitJsr_w(OpCodes.Jsr_w _jsr_w) {
        _jsr_w.branchbyte1 = byteReader.read1();
        _jsr_w.branchbyte2 = byteReader.read1();
        _jsr_w.branchbyte3 = byteReader.read1();
        _jsr_w.branchbyte4 = byteReader.read1();
    }

    @Override
    public void visitL2d(OpCodes.L2d _l2d) {

    }

    @Override
    public void visitL2f(OpCodes.L2f _l2f) {

    }

    @Override
    public void visitL2i(OpCodes.L2i _l2i) {

    }

    @Override
    public void visitLadd(OpCodes.Ladd _ladd) {

    }

    @Override
    public void visitLaload(OpCodes.Laload _laload) {

    }

    @Override
    public void visitLand(OpCodes.Land _land) {

    }

    @Override
    public void visitLastore(OpCodes.Lastore _lastore) {

    }

    @Override
    public void visitLcmp(OpCodes.Lcmp _lcmp) {

    }

    @Override
    public void visitLconst_0(OpCodes.Lconst_0 _lconst_0) {

    }

    @Override
    public void visitLconst_1(OpCodes.Lconst_1 _lconst_1) {

    }

    @Override
    public void visitLdc(OpCodes.Ldc _ldc) {
        _ldc.index = byteReader.read1();
    }

    @Override
    public void visitLdc_w(OpCodes.Ldc_w _ldc_w) {
        _ldc_w.indexbyte1 = byteReader.read1();
        _ldc_w.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitLdc2_w(OpCodes.Ldc2_w _ldc2_w) {
        _ldc2_w.indexbyte1 = byteReader.read1();
        _ldc2_w.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitLdiv(OpCodes.Ldiv _ldiv) {

    }

    @Override
    public void visitLload(OpCodes.Lload _lload) {
        _lload.index = byteReader.read1();
    }

    @Override
    public void visitLload_0(OpCodes.Lload_0 _lload_0) {

    }

    @Override
    public void visitLload_1(OpCodes.Lload_1 _lload_1) {

    }

    @Override
    public void visitLload_2(OpCodes.Lload_2 _lload_2) {

    }

    @Override
    public void visitLload_3(OpCodes.Lload_3 _lload_3) {

    }

    @Override
    public void visitLmul(OpCodes.Lmul _lmul) {

    }

    @Override
    public void visitLneg(OpCodes.Lneg _lneg) {

    }

    @Override
    public void visitLookupswitch(OpCodes.Lookupswitch _lookupswitch) {
        byteReader.padding();
        _lookupswitch.defaultbyte1 = byteReader.readU4().getValue();
        _lookupswitch.defaultbyte2 = byteReader.readU4().getValue();
        _lookupswitch.defaultbyte3 = byteReader.readU4().getValue();
        _lookupswitch.defaultbyte4 = byteReader.readU4().getValue();
        _lookupswitch.npairs1 = byteReader.readU4().getValue();
        _lookupswitch.npairs2 = byteReader.readU4().getValue();
        _lookupswitch.npairs3 = byteReader.readU4().getValue();
        _lookupswitch.npairs4 = byteReader.readU4().getValue();

        // todo match offset pairs 还不是很明白
    }

    @Override
    public void visitLor(OpCodes.Lor _lor) {

    }

    @Override
    public void visitLrem(OpCodes.Lrem _lrem) {

    }

    @Override
    public void visitLreturn(OpCodes.Lreturn _lreturn) {

    }

    @Override
    public void visitLshl(OpCodes.Lshl _lshl) {

    }

    @Override
    public void visitLshr(OpCodes.Lshr _lshr) {

    }

    @Override
    public void visitLstore(OpCodes.Lstore _lstore) {
        _lstore.index = byteReader.read1();
    }

    @Override
    public void visitLstore_0(OpCodes.Lstore_0 _lstore_0) {

    }

    @Override
    public void visitLstore_1(OpCodes.Lstore_1 _lstore_1) {

    }

    @Override
    public void visitLstore_2(OpCodes.Lstore_2 _lstore_2) {

    }

    @Override
    public void visitLstore_3(OpCodes.Lstore_3 _lstore_3) {

    }

    @Override
    public void visitLsub(OpCodes.Lsub _lsub) {

    }

    @Override
    public void visitLushr(OpCodes.Lushr _lushr) {

    }

    @Override
    public void visitLxor(OpCodes.Lxor _lxor) {

    }

    @Override
    public void visitMonitorenter(OpCodes.Monitorenter _monitorenter) {

    }

    @Override
    public void visitMonitorexit(OpCodes.Monitorexit _monitorexit) {

    }

    @Override
    public void visitMultianewarray(OpCodes.Multianewarray _multianewarray) {
        _multianewarray.indexbyte1 = byteReader.read1();
        _multianewarray.indexbyte2 = byteReader.read1();
        _multianewarray.dims = byteReader.read1();
    }

    @Override
    public void visitNew(OpCodes.New _new) {
        _new.indexbyte1 = byteReader.read1();
        _new.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitNewarray(OpCodes.Newarray _newarray) {
        _newarray.atype = byteReader.read1();
    }

    @Override
    public void visitNop(OpCodes.Nop _nop) {

    }

    @Override
    public void visitPop(OpCodes.Pop _pop) {

    }

    @Override
    public void visitPop2(OpCodes.Pop2 _pop2) {

    }

    @Override
    public void visitPutfield(OpCodes.Putfield _putfield) {
        _putfield.indexbyte1 = byteReader.read1();
        _putfield.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitPutstatic(OpCodes.Putstatic _putstatic) {
        _putstatic.indexbyte1 = byteReader.read1();
        _putstatic.indexbyte2 = byteReader.read1();
    }

    @Override
    public void visitRet(OpCodes.Ret _ret) {
        _ret.index = byteReader.read1();
    }

    @Override
    public void visitReturn(OpCodes.Return _return) {

    }

    @Override
    public void visitSaload(OpCodes.Saload _saload) {

    }

    @Override
    public void visitSastore(OpCodes.Sastore _sastore) {

    }

    @Override
    public void visitSipush(OpCodes.Sipush _sipush) {
        _sipush.sbyte1 = byteReader.read1();
        _sipush.sbyte2 = byteReader.read1();
    }

    @Override
    public void visitSwap(OpCodes.Swap _swap) {

    }

    @Override
    public void visitTableswitch(OpCodes.Tableswitch _tableswitch) {
        byteReader.padding();
        // todo table switch
    }

    @Override
    public void visitWide(OpCodes.Wide _wide) {

    }
}
