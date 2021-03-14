using System;

namespace TimeCapsule.Domain.Common
{
    public abstract class AudiableEntity
    {
        //// 不是CLR类型，会在数据库中自动赋值，改为Nullable就不会了
        
        public DateTime LastModified { get; set; }
    }
}
