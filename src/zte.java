import static java.lang.System.out;

public class zte {

    public static void zteTest(long idx) {
        long temp = idx;
        out.println("原始信息index：" + temp);
        IndexInfo t1 = PonComplexIndex(temp, "C320");
//        IndexInfo t1 = PlatformComplexIndex(temp, "C320");

        out.println("分解后信息 t1=" + t1.toString());

    }

    /**
     * Pon复合索引x的对应物理口的分析 和iftable不一样的
     *
     * @param index
     * @param type  中兴设备类型
     * @return
     */
    public static IndexInfo PonComplexIndex(long index, String type) {
        long ix = (long) index;
        IndexInfo info = new IndexInfo();
        info.IndexType = (ix >> 28);

        switch (Integer.parseInt(String.valueOf(info.IndexType))) {
            case 1:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 16;
                info.PonNo = ((ix & 0xFFFF) >> 8);
                break;
            case 3:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 19;
                info.PonNo = ((ix & 0x7FFFF) >> 16);
                info.OnuNo = ((ix & 0xFFFF) >> 8);

                // if (OltType == ZteOltType.C300 || OltType == ZteOltType.C320)
                info.PonNo += 1;
                break;
            case 4:
                info.SheIfNo = (ix & 0x40000000) >> 30;
                info.CardSlotNo = (ix & 0x780000) >> 19;
                info.PonNo = ((ix & 0x70000) >> 16);
                info.OnuNo = ((ix & 0xFFFF) >> 8);

                info.PonNo += 1;
                break;
            case 6:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 16;
                // info.PonNo = 0;//((ix & 0xFFFF) >> 8) ;
                // info.OnuNo = 0;//(ix & 0xFF) ;
                break;
            case 7:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 16;
                info.TempletNo = ix & 0xFFFF;
                // info.PonNo = 0;//((ix & 0xFFFF) >> 8) ;
                // info.OnuNo = 0;//(ix & 0xFF) ;
                break;
            case 9:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 25;
                info.CardSlotNo = (ix & 0x1FFFFFF) >> 20;
                info.PonNo = (ix & 0xFFFFF) >> 16;
                info.OnuNo = (ix & 0xFFFF) >> 8;

                if (type.toUpperCase() == "C300" || type == "C320")
                    info.PonNo += 1;
                break;
            default:
                break;
        }

        switch (type.toUpperCase()) {
            case "C200":
            case "C220":
                info.SheIfNo = 0;
                break;
            case "C300":
            case "C320":
                info.SheIfNo = 1;
                break;
            default:
                break;
        }

        return info;
    }

    /**
     * 保存端口信息， 根据SNMP得到的index值 获取index对应的类型、机框号Shelf、物理槽位Slot值、物理板卡card值、
     * Pon口的索引PonIndex、Onu的索引OnuIndex、端口的索引IfIndex
     *
     * @author Sai
     */
    public static class IndexInfo {
        /// <summary>
        /// index对应的类型
        /// Enum.GetName(typeof(ObjType),2}
        /// </summary>
        public long IndexType;
        /// <summary>
        /// 机架号
        /// </summary>
        public long RackNo;
        /// <summary>
        /// 机框号
        /// C200/C220机架号和机框号从0开始编号，C300从1开始编号
        /// </summary>
        public long SheIfNo;
        /// <summary>
        /// 物理板卡槽位号
        /// </summary>
        public long CardSlotNo;
        /// <summary>
        /// 物理PON口号
        /// </summary>
        public long PonNo;
        /// <summary>
        /// 物理Onu口的号
        /// </summary>
        public long OnuNo;
        /// <summary>
        /// 端口的号IfNo
        /// </summary>
        public long IfNo;
        /// <summary>
        /// 模板号
        /// </summary>
        public long TempletNo;

        public long GetIndexType() {
            return IndexType;
        }

        public void SetIndexType(int indexType) {
            this.IndexType = indexType;
        }

        public long GetRackNo() {
            return RackNo;
        }

        public void SetRackNo(int rackNo) {
            this.RackNo = rackNo;
        }

        public long GetSheIfNo() {
            return SheIfNo;
        }

        public void SetSheIfNo(int sheIfNo) {
            this.SheIfNo = sheIfNo;
        }

        public long GetCardSlotNo() {
            return CardSlotNo;
        }

        public void SetCardSlotNo(int cardSlotNo) {
            this.CardSlotNo = cardSlotNo;
        }

        public long GetPonNo() {
            return PonNo;
        }

        public void SetPonNo(int ponNo) {
            this.PonNo = ponNo;
        }

        public long GetOnuNo() {
            return OnuNo;
        }

        public void SetOnuNo(int onuNo) {
            this.OnuNo = onuNo;
        }

        public long GetIfNo() {
            return IfNo;
        }

        public void SetIfNo(int ifNo) {
            this.IfNo = ifNo;
        }

        public long GetTempletNo() {
            return TempletNo;
        }

        public void SetTempletNo(int templetNo) {
            this.TempletNo = templetNo;
        }
        @Override
        public String toString() {
            return "IndexInfo{" +
                    " IndexType=" + IndexType +
                    ", RackNo=" + RackNo +
                    ", SheIfNo=" + SheIfNo +
                    ", CardSlotNo=" + CardSlotNo +
                    ", PonNo=" + PonNo +
                    ", OnuNo=" + OnuNo +
                    ", IfNo=" + IfNo +
                    ", TempletNo=" + TempletNo +
                    '}';
        }
    }

    /**
     * 中兴复合索引分解 和iftable一样的
     *
     * @param index
     * @param type  中兴类型
     * @return
     */
    public static IndexInfo PlatformComplexIndex(long index, String type) {
        long ix = (long) index;
        IndexInfo info = new IndexInfo();
        info.IndexType = (ix >> 28);

        switch (Integer.parseInt(String.valueOf(info.IndexType))) {
            case 1:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 16;
                info.PonNo = ((ix & 0xFFFF) >> 8) + 1;
                break;
            case 3:
            case 4:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 24;
                info.CardSlotNo = (ix & 0xFFFFFF) >> 19;
                info.PonNo = ((ix & 0x7FFFF) >> 16) + 1;
                info.OnuNo = ((ix & 0xFFFF) >> 8) + 1;
                info.IfNo = ((ix & 0xFF)) + 1;
                break;
            case 9:
            case 10:
                info.SheIfNo = (ix & 0xFFFFFFF) >> 25;
                info.CardSlotNo = (ix & 0x1FFFFFF) >> 20;
                info.PonNo = ((ix & 0xFFFFF) >> 16) + 1;
                info.OnuNo = ((ix & 0xFFFF) >> 8) + 1;
                break;
            default:
                break;
        }

        switch (type.toUpperCase()) {
            case "C200":
                info.SheIfNo = 0;
                info.CardSlotNo = ConvertSlot2PhyC200(info.CardSlotNo);
                break;
            case "C220":
                info.SheIfNo = 0;
                info.CardSlotNo = ConvertSlot2PhyC220(info.CardSlotNo);
                break;
            case "C300":
                info.SheIfNo = 1;
                info.CardSlotNo = ConvertSlot2PhyC300(info.CardSlotNo);
                break;
            case "C320":
                info.SheIfNo = 1;
                info.CardSlotNo = ConvertSlot2PhyC320(info.CardSlotNo);
                break;
            default:
                break;
        }
        return info;
    }

    /**
     * 把 "复合索引slot" 转成对应的 “物理槽位值” 不是一一对应，主控板会对应不上
     *
     * @param complexSlotIndex
     * @return
     */
    private static long ConvertSlot2PhyC200(long complexSlotIndex) {
        long result = 0;
        try {
            if (complexSlotIndex == 5)
                result = 6;
            else if (complexSlotIndex >= 1 && complexSlotIndex <= 3)
                result = complexSlotIndex;
            else if (complexSlotIndex == 0 || complexSlotIndex == 4)
                result = 4; // 这里不准确
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 把 "复合索引slot" 转成对应的 “物理槽位值” 不是一一对应，主控板会对应不上
     *
     * @param complexSlotIndex
     * @return
     */
    private static long ConvertSlot2PhyC220(long complexSlotIndex) {
        long result = 0;
        try {
            if (complexSlotIndex >= 6 && complexSlotIndex <= 11)
                result = complexSlotIndex + 3;
            else if (complexSlotIndex >= 0 && complexSlotIndex <= 5)
                result = complexSlotIndex + 1; // 这里不准确
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param complexSlotIndex
     * @return
     */
    private static long ConvertSlot2PhyC300(long complexSlotIndex) {
        long result = 0;

        try {
            if (complexSlotIndex >= 0 && complexSlotIndex <= 7)
                result = complexSlotIndex + 2;
            else if (complexSlotIndex >= 8 && complexSlotIndex <= 17)
                result = complexSlotIndex + 4;
            else if (complexSlotIndex == 0)
                result = 1;// 1 、10、11 这里不准确
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

    /**
     * @param complexSlotIndex
     * @return
     */
    private static long ConvertSlot2PhyC320(long complexSlotIndex) {
        long result = 0;
        try {
            if (complexSlotIndex >= 0 && complexSlotIndex <= 5)
                result = complexSlotIndex + 1;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return result;
    }

}
