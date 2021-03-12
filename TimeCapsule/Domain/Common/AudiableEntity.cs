using System;

namespace TimeCapsule.Domain.Common
{
    public abstract class AudiableEntity
    {
        public string CreatedBy { get; set; }

        public DateTime Created { get; set; }

        public string LastModifiedBy { get; set; }

        // 不是CLR类型，会在数据库中自动赋值，改为Nullable就不会了
        public DateTime? LastModified { get; set; }
    }
}
